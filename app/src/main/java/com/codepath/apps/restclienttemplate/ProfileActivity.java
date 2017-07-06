package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");

        //create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        //display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //make change
        ft.replace(R.id.flContainer, userTimelineFragment);

        //commit
        ft.commit();

        client = TwitterApp.getRestClient();
        if(TextUtils.isEmpty(screenName))
        {
            client.getUserInfo(new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    //deserialize the User object
                    try
                    {
                        User user = User.fromJSON(response);

                        //set the title of the ActionBar based on the user information
                        getSupportActionBar().setTitle(user.screenName);

                        //populate the user headline
                        populateUserHeadline(user);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            });
        }
        else
        {
            client.getTweeterInfo(screenName, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    //deserialize the User object
                    try
                    {
                        User user = User.fromJSON(response);

                        //set the title of the ActionBar based on the user information
                        getSupportActionBar().setTitle(user.screenName);

                        //populate the user headline
                        populateUserHeadline(user);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    public void populateUserHeadline(User user)
    {
        TextView TVname = (TextView) findViewById(R.id.TVname);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView IVprofileImage = (ImageView) findViewById(R.id.IVprofileImage);
        TVname.setText(user.name);

        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + "Followers");
        tvFollowing.setText(user.followingCount + "Following");

        Glide.with(this).load(user.profileImageUrl).bitmapTransform(new RoundedCornersTransformation(context, 200, 0)).into(IVprofileImage);



    }
}
