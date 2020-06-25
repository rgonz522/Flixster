package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity
{

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> Movies;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       com.example.flixster.databinding.ActivityMainBinding binding = com.example.flixster.databinding.ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);


        Movies = new ArrayList<>();

        // Create Adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, Movies);

        // Set adapter on recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set Layout manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {
                Log.d(TAG, "OnSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        Log.d(TAG, results.toString());
                        movieAdapter.notifyDataSetChanged();
                        Movies.addAll(Movie.fromJsonArray(results));

                        Log.d(TAG, "Movies" + Movies.size());
                     } catch (JSONException e)
                        {
                            Log.d(TAG, "Hit JSON Exception");
                            e.printStackTrace();

                        }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable)
            {
                Log.d(TAG, "OnFailure");
            }
        });
    }
}