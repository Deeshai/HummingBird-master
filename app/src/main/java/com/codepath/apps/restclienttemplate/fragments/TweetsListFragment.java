package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by deeshaiesc on 7/3/17.
 */

public class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {


    public interface TweetSelectedListener
    {
        //handle tweet selection
        void onTweetSelected(Tweet tweet);
        void onImageSelected(Tweet tweet);
    }

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView RVtweet;

    SwipeRefreshLayout swipeContainer;
    //inflation happens inside onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully
                //tweetAdapter.clear();
                populateTimeLine();

            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
                android.R.color.black,
                android.R.color.holo_red_dark);

        //swipeContainer.setRefreshing(false);


        //find the RecyclerView
        RVtweet = (RecyclerView) v.findViewById(R.id.RVtweet);

        //init the arrayList (data source)
        tweets = new ArrayList<>();

        //construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets, this);

        RVtweet.setLayoutManager(new LinearLayoutManager(getContext()));
        //RecyclerView setup (layout manager, user adapter)
        RVtweet.setAdapter(tweetAdapter);

        return v;

    }

    public void populateTimeLine()
    {
        return;
    }


    public void addItems(JSONArray response)
    {
        for (int i = 0; i < response.length(); i++) {
            //convert each object to a Tweet model
            //add that Tweet model to our data source
            //notify the adapter that we've add an item

            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onItemSelected(View view, int position, boolean isPic)
    {
        Tweet tweet = tweets.get(position);
        if(!isPic)
        {
            ((TweetSelectedListener)getActivity()).onTweetSelected(tweet);
        }
        else
        {
            ((TweetSelectedListener)getActivity()).onImageSelected(tweet);
        }

    }

}
