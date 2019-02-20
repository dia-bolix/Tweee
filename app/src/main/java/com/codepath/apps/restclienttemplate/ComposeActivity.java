package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    //variables to point to the compose button
    private EditText edit_compose;
    private Button btntwe;
    private TwitterClient client;
    public static final int MAX_CHAR = 140;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_activity);

        client = TwitterApp.getRestClient(this);
        edit_compose = findViewById(R.id.mlCompose);
        btntwe = findViewById(R.id.butTweet);
        //when clicking on twee, add the twee to the top of my timeline
        btntwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tweet_post = edit_compose.getText().toString();
                //check text is vaild
                if (tweet_post.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "You didn't write anything!" , Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweet_post.length() > MAX_CHAR) {
                    Toast.makeText(ComposeActivity.this, "Your tweet exceeds the character limit", Toast.LENGTH_LONG).show();
                }
            Toast.makeText(ComposeActivity.this,tweet_post, Toast.LENGTH_LONG).show();
            //now make the api call to add the tweet
            //now that we have the items in the tweet, actually update the timeline
            //so make a call to the api
            client.composeTweee(tweet_post, new JsonHttpResponseHandler() {
                @Override
                //if it worked, do this
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJson(response);
                        Intent data = new Intent();
                        //return data back as the result in main timeline
                        data.putExtra("tweet", Parcels.wrap(tweet));
                        //sets the result code as ok, returns data which is the tweet
                        setResult(RESULT_OK, data);
                        //now close the activity
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
            }
        });

    }
}
