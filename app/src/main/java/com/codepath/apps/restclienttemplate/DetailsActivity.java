package com.codepath.apps.restclienttemplate;

import android.media.Image;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpClient;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity
{

    //instance fields
    AsyncHttpClient client;

    //the tweet to display
    Tweet tweet;

    @Nullable @BindView(R.id.IVprofileImage) ImageView IVprofileImage;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.TVname) TextView TVname;
    @BindView(R.id.tvTagline) TextView tvTagline;
    @BindView(R.id.tvCreatedAt) TextView tvCreatedAt;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        //initialize the client
        client = new AsyncHttpClient();

        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d("Details Activity", String.format("Showing details for %s's tweet", tweet.user.screenName));

        //set the title and overview
        tvBody.setText(tweet.body);
        tvCreatedAt.setText(tweet.createdAt);
        tvTagline.setText(tweet.user.screenName);
        TVname.setText(tweet.user.name);

        String profileImageUrl = tweet.user.profileImageUrl;

        Glide.with(this).load(profileImageUrl).bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 200, 0)).into(IVprofileImage);

    }
}
