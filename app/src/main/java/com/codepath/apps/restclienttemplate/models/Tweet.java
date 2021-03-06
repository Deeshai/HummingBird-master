package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by deeshaiesc on 6/26/17.
 */


@Parcel

public class Tweet
{
    //list out the attributes

    public String body;
    public long uid; //database ID for the tweet

    public User user;
    public String createdAt;
    public String relativeDate;
    //public String screenName;

    public Tweet()
    {

    }

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException
    {
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.relativeDate = TimeFormatter.getTimeDifference(tweet.createdAt);
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }


}
