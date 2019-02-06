package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    //pass in context and list of tweets
    private Context context;
    private List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, viewGroup, false);
        return new ViewHolder(view);
    }

    //bind value based on pos of element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tweet tweet = tweets.get(i);
        viewHolder.tweet_content.setText(tweet.body);
        viewHolder.handle.setText(tweet.user.screenname);
        viewHolder.relativedate.setText(tweet.createdDate);
        Glide.with(context).load(tweet.user.profileImgUrl).into(viewHolder.ivProfileImg);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //add methods to replace when swipe to refresh

    //clear the list of tweets
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    //add the new list in
    public void addTweets(List<Tweet> tweetlist) {
        tweets.addAll(tweetlist);
        notifyDataSetChanged();
    }



    //define a viewholder to be displayed in recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfileImg;
        public TextView handle;
        public TextView tweet_content;
        public TextView relativedate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImg = itemView.findViewById(R.id.ivProfile);
            handle = itemView.findViewById(R.id.tvHandle);
            tweet_content = itemView.findViewById(R.id.tvUserTweet);
            relativedate = itemView.findViewById(R.id.tvTime);

        }
    }
}
