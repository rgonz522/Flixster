package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity
{
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;

    String videoKey;
    final String API_URL_Beginning = "https://api.themoviedb.org/3/movie/";
    final String API_URL_End = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);


        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());


        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        //set the picture
        int radius = 60; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

        //  Glide.with(this).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(radius, margin))
        //.into(ivPoster);
        String imgurl = movie.getPosterPath();

        Glide.with(this).load(imgurl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

        String endpoint_url = API_URL_Beginning + movie.getId() + API_URL_End;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(endpoint_url, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MovieDetailsActivity", "OnSuccess");
                JSONObject jsonObject = json.jsonObject;


                try {
                    JSONObject result = jsonObject.getJSONArray("results").getJSONObject(0);
                    Log.d("MovieDetailsActivity", "results:" + result.toString());
                    videoKey = "" + result.getString("key");
                    Log.d("MovieDetailsActivity", "videoKey" + videoKey);


                } catch (JSONException e) {
                    Log.d("MovieDetailsActivity", "Hit JSON Exception");
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieTrailerActivity", "API OnFailure");

            }

        });


        ivPoster.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);

                Log.d("MovieDetailsActivity", "onClick: " + videoKey);
                intent.putExtra("videoKey", videoKey);
                startActivity(intent);
            }
        });
    }


}