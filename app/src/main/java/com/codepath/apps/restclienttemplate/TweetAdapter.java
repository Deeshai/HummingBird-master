package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by deeshaiesc on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
{

    private List <Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    //define an interface required by the ViewHolder
    public interface TweetAdapterListener
    {
        void onItemSelected(View view, int position, boolean isPick);
    }

    //pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener)
    {
        mTweets = tweets;
        this.mListener = listener;
    }

    //for each row, inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        //get the data according to position
        Tweet tweet = mTweets.get(position);

        //populate the views according to this data
        holder.TVuserName.setText(tweet.user.name);
        holder.TVbody.setText(tweet.body);
        holder.TVcreated.setText(tweet.relativeDate);
        holder.TVhandle.setText(tweet.user.screenName);

        holder.IVprofileImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onItemSelected(view, position, true);
            }

        });

        Glide.with(context).load(tweet.user.profileImageUrl).bitmapTransform(new RoundedCornersTransformation(context, 200, 0)).into(holder.IVprofileImage);

    }

    @Override
    public int getItemCount()
    {
        return mTweets.size();
    }

    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.IVprofileImage) ImageView IVprofileImage;
        @BindView(R.id.TVuserName) TextView TVuserName;
        @BindView(R.id.TVbody) TextView TVbody;
        @BindView(R.id.TVcreated) TextView TVcreated;
        @BindView(R.id.TVhandle) TextView TVhandle;

        public ViewHolder(View itemView)
        {
            super(itemView);

            //perform findViewById lookups
            ButterKnife.bind(this,itemView);

            //handle row click event
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mListener != null)
                    {
                        //get the position of row element
                        int position = getAdapterPosition();

                        //fire the listener callback
                        mListener.onItemSelected(view, position, false);
                    }
                }

            });
        }

    }

    public void clear()
    {
        mTweets.clear();
        notifyDataSetChanged();
    }

}
