package com.example.vinayg.tmdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;


/**
 * Created by vinay.g.
 */

public class PopularVIewAdapter extends RecyclerView.Adapter<PopularVIewAdapter.ViewHolder>{
    public Context context;
    private ArrayList<Movie> data;

    public PopularVIewAdapter(Context context, ArrayList<Movie> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Button likeBtn = holder.likeBtn ;
        ImageView image = holder.image;
        TextView tvVotesCount = holder.votesCount;
        TextView imageTitle = holder.imageTitle;
        TextView genres = holder.release_date;
        TextView tvLanguage = holder.tvLanguage;
        Movie movie = data.get(position);
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        Boolean isSaved = database.checkIfsaved(movie);
        if (isSaved) {
            likeBtn.setBackgroundResource(R.drawable.like);
        } else {
            likeBtn.setBackgroundResource(R.drawable.likegrey);
        }
        tvLanguage.setText(movie.getLanguage().toUpperCase());
        Glide
                .with(context)
                .load(movie.getImageUrl())
                .into(image);

        imageTitle.setText(movie.getTitle());
        genres.setText(movie.getGenre());
        tvVotesCount.setText(movie.getVotes());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {
        final TextView imageTitle;
        final ImageView image;
        final TextView release_date;
        final Button likeBtn;
        final TextView votesCount;
        final TextView tvLanguage;
        ViewHolder(View itemView) {
            super(itemView);
            imageTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            image = (ImageView) itemView.findViewById(R.id.imgBanner);
            release_date = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
            likeBtn = (Button) itemView.findViewById(R.id.btnLike);
            votesCount = (TextView) itemView.findViewById(R.id.tvVotesCount);
            tvLanguage = (TextView) itemView.findViewById(R.id.tvLanguage);
        }
    }
}

