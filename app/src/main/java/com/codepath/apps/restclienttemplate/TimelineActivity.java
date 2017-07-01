package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 20 ;
    TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView RVtweet;
    private SwipeRefreshLayout swipeContainer;
    Menu  menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeLine();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
                android.R.color.black,
                android.R.color.holo_red_dark);





        client = TwitterApp.getRestClient();

        //find the RecyclerView
        RVtweet = (RecyclerView) findViewById(R.id.RVtweet);

        //init the arrayList (data source)
        tweets = new ArrayList<>();

        //construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets);

        RVtweet.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView setup (layout manager, user adapter)
        RVtweet.setAdapter(tweetAdapter);
        populateTimeLine();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View view) {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /// REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {


            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));

            tweets.add(0,tweet);
            tweetAdapter.notifyItemInserted(0);
            RVtweet.getLayoutManager().scrollToPosition(0);

            Toast.makeText(this, "HUM!!!", Toast.LENGTH_SHORT).show();

        }
    }


    private void populateTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                //iterate through the JSON array
                tweetAdapter.clear();
                //for each entry, deserialize the JSON object
                for (int i = 0; i < response.length(); i++) {
                    //convert each object to a Tweet model
                    //add that Tweet model to our data source
                    //notify the adapter that we've add an item
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    swipeContainer.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

        });

        }


}


