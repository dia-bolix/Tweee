package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainTimeline extends AppCompatActivity {

    //create an instance of the twitter client
    private TwitterClient client;
    RecyclerView rvTweets;
    private TweetAdapter adapter;
    private List<Tweet> tweets;
    private SwipeRefreshLayout swipeView;
    public final int REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_timeline);

        client = TwitterApp.getRestClient(this);
        swipeView = findViewById(R.id.swipe);

        //find recyclerview
        rvTweets = findViewById(R.id.rvTweets);
        //init list of tweets & adapter from data src
        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        // setup the recyclerview (1) layout manager (2) adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);

        populateHomeTimeline();

        //iif the user swipes
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Swiper", "content succ");
                populateHomeTimeline();
            }
        });
    }


    //add the compose (menu) button to this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //check if the item selected matches id of icon
        if (item.getItemId() == R.id.compose) {
            Intent i = new Intent(MainTimeline.this, ComposeActivity.class);
            this.startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //get the twitter object back from intent


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            //get out the tweet object
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //update the recycler view
            tweets.add(0, tweet);
            adapter.notifyItemInserted(0);
            //make it so you don't need to scroll up
            rvTweets.smoothScrollToPosition(0);

        }
    }

    //a function which populates the recycler view
    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                List<Tweet> tweetsToAdd = new ArrayList<>();
                //iterate through json arr
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonTweet = response.getJSONObject(i);
                        //convert json obj to tweet obj
                        Tweet tweet = Tweet.fromJson(jsonTweet);
                        //add tweet into data src (list of tweets)
                        tweetsToAdd.add(tweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.clear();
                adapter.addTweets(tweetsToAdd);
                swipeView.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
            }
        });
    }
}
