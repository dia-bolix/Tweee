package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    //variables to point to the compose button
    private EditText edit_compose;
    private Button btntwe;
    public static final int MAX_CHAR = 140;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_activity);

        edit_compose = findViewById(R.id.mlCompose);
        btntwe = findViewById(R.id.butTweet);
        //when clicking on twee, add the twee to the top of my timeline
        btntwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweet_post = edit_compose.getText().toString();
                //check text is vaild
                if (tweet_post.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "You didn't write anything!" , Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweet_post.length() > MAX_CHAR) {
                    Toast.makeText(ComposeActivity.this, "Your tweet exceeds the character limit", Toast.LENGTH_LONG).show();
                }
            Toast.makeText(ComposeActivity.this,tweet_post, Toast.LENGTH_LONG).show();          //now make the api call to add the tweet
            }
        });

    }
}
