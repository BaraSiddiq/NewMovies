package com.example.android.movie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movie.Classes.Movie;
import com.example.android.movie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    List<Movie> movies;
    OnHolderListener onHolderListener;


    public MovieAdapter() {
    }

    public MovieAdapter(List<Movie> movies, OnHolderListener onHolderListener) {
        this.movies = movies;
        this.onHolderListener = onHolderListener;

    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        MovieHolder movieHolder = new MovieHolder(view, onHolderListener);

        return movieHolder;
    }

    @Override
    public void onBindViewHolder(MovieHolder movieHolder, int i) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movies.get(i).getImageUrl()).into(movieHolder.img);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        OnHolderListener onHolderListener;


        public MovieHolder(View itemView, OnHolderListener onHolderListener) {
            super(itemView);
            this.onHolderListener = onHolderListener;
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHolderListener.onHolderClick(getAdapterPosition());
        }
    }


    public interface OnHolderListener {
        void onHolderClick(int position);
    }


    public void setMovies(List<Movie> movies) {


        this.movies = movies;
    }


}
