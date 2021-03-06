package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener {


    private static final int REQUEST_CODE = 20 ;
    //private SwipeRefreshLayout swipeContainer;
    Menu  menu;
    ViewPager vpPager;
    TweetsPagerAdapter adapterViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);


//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully
//
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
//                android.R.color.holo_orange_light,
//                android.R.color.black,
//                android.R.color.holo_red_dark);
//
//        swipeContainer.setRefreshing(false);

        //get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);

        //set the adapter for the pager
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(adapterViewPager);

        //setup TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        adapterViewPager.getRegisteredFragment(0);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }


    public void onHum(View view)
    {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /// REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {


            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));

            // Pass new tweet into the home timeline and add to top of the list
            HomeTimelineFragment fragmentHomeTweets =
                    (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTweets.appendTweet(tweet);

            Toast.makeText(this, "HUM!!!", Toast.LENGTH_SHORT).show();

        }
    }

    public void onProfileView(MenuItem item)
    {
        //launch the profile view
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onImageSelected(Tweet tweet)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name",tweet.user.screenName);
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet)
    {
        Intent intent = new Intent(TimelineActivity.this, DetailsActivity.class);
        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
        startActivity(intent);
    }

}





