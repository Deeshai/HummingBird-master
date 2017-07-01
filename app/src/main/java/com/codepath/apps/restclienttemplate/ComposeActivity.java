package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import org.parceler.Parcel;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import static com.codepath.apps.restclienttemplate.TwitterApp.getRestClient;




public class ComposeActivity extends AppCompatActivity
{
    TextView TVcharCount;
    EditText ETtweet;
    Button   BThum;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ETtweet = (EditText) findViewById(R.id.ETtweet);
        TVcharCount = (TextView) findViewById(R.id.TVcharCount);
        ETtweet.addTextChangedListener(mTextEditorWatcher);
        BThum = (Button) findViewById(R.id.BThum);
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            TVcharCount.setText(String.valueOf(140-s.length()));

            if(s.length() > 140)
            {
                TVcharCount.setTextColor(Color.RED);
                BThum.setAlpha(.5f);
                BThum.setBackgroundColor(Color.parseColor("#ab99cc00"));
                BThum.setClickable(false);

                Toast.makeText(ComposeActivity.this, "Too much humming",Toast.LENGTH_SHORT).show();
            }
            else
            {
                TVcharCount.setTextColor(Color.BLACK);
                BThum.setAlpha(1f);
                BThum.setBackgroundColor(Color.parseColor("#ff99cc00"));
                BThum.setClickable(true);
            }

        }

        public void afterTextChanged(Editable s) {
        }

    };



    public void Hum(View view)
    {
        TwitterClient client = getRestClient();

        EditText ETtweet = (EditText) findViewById(R.id.ETtweet);

        Intent data = new Intent();

        data.putExtra("tweet", ETtweet.getText().toString());

        client.sendTweet(ETtweet.getText().toString(), new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {

                try
                {
                    Tweet tweet = Tweet.fromJSON(response);


                    Intent intent = new Intent();
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Intent data = new Intent(ComposeActivity.this, TimelineActivity.class);

                Log.d("TwitterClient", response.toString());


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

    public void CloseComposed(View view)
    {

        finish();
    }
}


//    YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
//
//    //intitialize with API key stored in secrets.xml
//        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener()
//    {
//        @Override
//        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
//        {
//            youTubePlayer.cueVideo(videoId);
//        }
//
//        @Override
//        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
//        {
//            //log the error
//            Log.e("MovieTrailerActivity", "Error initializing YouTube player");
//        }
//
//    });




