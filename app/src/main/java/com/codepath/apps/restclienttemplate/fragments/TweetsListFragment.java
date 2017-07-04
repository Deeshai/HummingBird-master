package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by deeshaiesc on 7/3/17.
 */

public class TweetsListFragment extends Fragment {
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView RVtweet;
    //inflation happens inside onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);


        //find the RecyclerView
        RVtweet = (RecyclerView) v.findViewById(R.id.RVtweet);

        //init the arrayList (data source)
        tweets = new ArrayList<>();

        //construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets);

        RVtweet.setLayoutManager(new LinearLayoutManager(getContext()));
        //RecyclerView setup (layout manager, user adapter)
        RVtweet.setAdapter(tweetAdapter);

        return v;
    }

    public void nM
            (Tweet tweet){

        tweets.add(0,tweet);
        tweetAdapter.notifyItemInserted(0);
        RVtweet.getLayoutManager().scrollToPosition(0);
    }

    public void addItems(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            //convert each object to a Tweet model
            //add that Tweet model to our data source
            //notify the adapter that we've add an item

           // tweetAdapter.clear();

            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
