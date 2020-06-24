package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    Context context;
    List<Movie> movies;


    public MovieAdapter (Context context, List<Movie> movies)
    {
        this.context = context;
        this.movies = movies;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d("MovieAdapter", "OnCreateViewHolder");
       View movieview =  LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Log.d("MovieAdapter", ("OnBindViewHolder" + position));
        //getmovie @ postion
        Movie movie = movies.get(position);
        //bind moviedata into view holder
        holder.bind(movie);



    }

    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        TextView tvOverView;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvTitle  = itemView.findViewById(R.id.tvTitle);
            tvOverView = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);


        }

        public void bind(Movie movie)
        {
            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverview());


            String imgurl;

            //if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                    imgurl = movie.getBackgroundPath();
            }
            else
            {
                    imgurl = movie.getPosterPath();
            }
            // then imageUrl = background
            //then imageUrl = poster image

            Glide.with(context).load(imgurl).into(ivPoster);
        }
    }
}
