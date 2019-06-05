package com.example.android.movie.Foreground;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movie.Adapters.ReviewAdapter;
import com.example.android.movie.Adapters.TrailerAdapter;
import com.example.android.movie.Classes.Movie;
import com.example.android.movie.Classes.Review;
import com.example.android.movie.Classes.Trailer;
import com.example.android.movie.Data.Database;
import com.example.android.movie.R;
import com.example.android.movie.Utils.JsonUtil;
import com.example.android.movie.Utils.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {
    ImageView bg;
    ImageView img;
    ImageView favorite;
    TextView title, date, rate, plot;
    Movie movie;
    MovieViewModel movieViewModel;
    boolean isClicked = false;

    int movieID = 0;

    List<Trailer> trailers;
    RecyclerView trailerRecyclerView;
    TrailerAdapter trailerAdapter;
    RecyclerView.LayoutManager trailerLayoutManager;

    List<Review> reviews;
    RecyclerView reviewRecyclerView;
    ReviewAdapter reviewAdapter;
    RecyclerView.LayoutManager reviewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        trailers = new ArrayList<>();
        trailerRecyclerView = findViewById(R.id.trailer_rv);
        trailerLayoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(trailers);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);

        reviews = new ArrayList<>();
        reviewRecyclerView = findViewById(R.id.review_rv);
        reviewLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(reviews);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setAdapter(reviewAdapter);


        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);


        img = (ImageView) findViewById(R.id.thumbnail);
        title = (TextView) findViewById(R.id.title_tv);
        date = (TextView) findViewById(R.id.date_tv);
        rate = (TextView) findViewById(R.id.rate_tv);
        plot = (TextView) findViewById(R.id.plot_tv);
        favorite = (ImageView) findViewById(R.id.favorite);


        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie.getImageUrl()).into(img);
        title.setText(movie.getTitle());
        date.setText(movie.getReleaseDate());
        rate.setText(movie.getRating() + "");
        plot.setText(movie.getPlot());
        movieID = movie.getId();

        isClicked = false;
        new getMovieTask().execute(movie);
        new TrailerTask().execute(2);
        new ReviewTask().execute(3);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClicked = true;
                new getMovieTask().execute(movie);


            }
        });


    }

    class getMovieTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movies) {

            Database db = Database.getDataBase(MovieActivity.this);
            Movie returnedMovie = db.movieDao().getMovie(movies[0].getId());
            return returnedMovie;
        }

        @Override
        protected void onPostExecute(Movie returnedMovie) {

            super.onPostExecute(returnedMovie);
            if (isClicked == true) {

                if (returnedMovie != null) {

                    favorite.setImageResource(R.drawable.unfav);
                    movieViewModel.delete(movie);


                } else {
                    favorite.setImageResource(R.drawable.fav);
                    movieViewModel.insert(movie);


                }
            } else {

                if (returnedMovie != null) {

                    favorite.setImageResource(R.drawable.fav);

                } else {
                    favorite.setImageResource(R.drawable.unfav);
                }
            }
        }
    }

    class TrailerTask extends AsyncTask<Integer, Void, List<Trailer>> {


        @Override
        protected List<Trailer> doInBackground(Integer... args) {
            String json = "";

            try {
                NetworkUtil.setMovieID(movieID);
                json = NetworkUtil.getHttpRespnse(args[0]);

            } catch (IOException e) {

                e.printStackTrace();
            }

            trailers = JsonUtil.jsonObjExtractorTrailers(json);

            return trailers;

        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            super.onPostExecute(trailers);


            trailerAdapter.setTrailers(trailers);
            trailerRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    class ReviewTask extends AsyncTask<Integer, Void, List<Review>> {


        @Override
        protected List<Review> doInBackground(Integer... args) {
            String json = "";

            try {
                NetworkUtil.setMovieID(movieID);
                json = NetworkUtil.getHttpRespnse(args[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            reviews = JsonUtil.jsonObjExtractorReview(json);

            return reviews;

        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);


            reviewAdapter.setReviews(reviews);
            reviewRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
