package com.example.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity{

    Movie movie;

    String API_URL_Beginning = "https://api.themoviedb.org/3/movie/";
    String API_URL_End = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    String videoKey = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        Intent intent = getIntent();
        videoKey = intent.getStringExtra("videoKey");

       // movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        //int videoId = movie.getId();

       // String endpoint_url = API_URL_Beginning + videoId + API_URL_End;
        //Log.d("MovieTrailerActivity", "onCreate: " + endpoint_url );


      /*  AsyncHttpClient client = new AsyncHttpClient();
        client.get(endpoint_url, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MovieDetailsActivity", "OnSuccess");
                JSONObject jsonObject = json.jsonObject;


                try {
                    JSONObject result = jsonObject.getJSONArray("results").getJSONObject(0);
                    Log.d("MovieTrailerActivity", "results:"+ result.toString());
                    videoKey = "" + result.getString("key");
                    Log.d("MovieTrailerActivity", "videoKey"+ videoKey);



                } catch (JSONException e) {
                    Log.d("MovieTrailerActivity", "Hit JSON Exception");
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieTrailerActivity", "API OnFailure");

            }

        });

*/
        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener()
            {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                Log.e("MovieTrailerActivity", "Videokey: " + videoKey);


                if(videoKey != null) {  youTubePlayer.cueVideo(videoKey);}

                Log.e("MovieTrailerActivity", "initializing YouTube player");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult)
            {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
            });
    }
}