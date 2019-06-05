package com.example.android.movie.Foreground;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.movie.Adapters.MovieAdapter;
import com.example.android.movie.Classes.Movie;
import com.example.android.movie.R;
import com.example.android.movie.Utils.JsonUtil;
import com.example.android.movie.Utils.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnHolderListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter movieAdapter;

    MovieTask movieTask = null;

    List<Movie> movies = new ArrayList<>();

    MovieViewModel movieViewModel;

    Parcelable recyclerState;

    int currentSort = 0;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieTask = new MovieTask();


        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> moviesReturned) {
                if (savedInstanceState != null) {
                    currentSort = savedInstanceState.getInt("S");
                    recyclerState = savedInstanceState.getParcelable("R");
                }

                if (currentSort == 2) {


                    movies = moviesReturned;
                    recyclerView = findViewById(R.id.rv);
                    recyclerView.setAdapter(new MovieAdapter(movies, MainActivity.this));
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));


                    if (recyclerState != null) {
                        Log.d("Test:", "Changed");
                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();


                }
            }


        });

        if (savedInstanceState != null) {
            currentSort = savedInstanceState.getInt("S");
            if(currentSort != 2) {
                movies = savedInstanceState.getParcelableArrayList("M");
                recyclerState = savedInstanceState.getParcelable("R");


                recyclerView = (RecyclerView) findViewById(R.id.rv);
                movieAdapter = new MovieAdapter(movies, MainActivity.this);

                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(movieAdapter);

                recyclerView.getAdapter().notifyDataSetChanged();

                if (recyclerState != null) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
                }

                currentSort = savedInstanceState.getInt("S");

            }
        } else {
            recyclerView = (RecyclerView) findViewById(R.id.rv);
            movies = new ArrayList<>();
            layoutManager = new GridLayoutManager(MainActivity.this, 2);
            movieAdapter = new MovieAdapter(movies, MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.getLayoutManager().onSaveInstanceState();
            recyclerView.setAdapter(movieAdapter);
            movieTask = new MovieTask();

            movieTask.execute(0);
        }


    }

    public class MovieTask extends AsyncTask<Integer, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(Integer... args) {
            String json = "";

            List<Movie> moviesReturned = new ArrayList<>();
            try {
                json = NetworkUtil.getHttpRespnse(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            moviesReturned = JsonUtil.jsonObjExtractorMovie(json);

            return moviesReturned;
        }

        @Override
        protected void onPostExecute(final List<Movie> moviesReturned) {
            super.onPostExecute(movies);
            movies = moviesReturned;
            movieAdapter = new MovieAdapter(movies, MainActivity.this);
            recyclerView.setAdapter(movieAdapter);
            recyclerView.getAdapter().notifyDataSetChanged();

        }

    }

    @Override
    public void onHolderClick(int position) {
        Movie movie = null;
        if (currentSort == 0 || currentSort == 1) {
            movie = movies.get(position);
        } else {
            movie = movies.get(position);
        }

        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("movie", movie);

        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt("S", currentSort);

        outState.putParcelableArrayList("M", (ArrayList<Movie>) movies);
        outState.putParcelable("R", recyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentSort = savedInstanceState.getInt("S");
        movies = savedInstanceState.getParcelableArrayList("M");
        recyclerState = savedInstanceState.getParcelable("R");
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.popular:
                currentSort = 0;
                movieTask.cancel(true);
                movieTask = new MovieTask();
                movieTask.execute(0);
                break;
            case R.id.vote:
                currentSort = 1;
                movieTask.cancel(true);
                movieTask = new MovieTask();
                movieTask.execute(1);
                break;
            case R.id.favorite_menu:

                movies = movieViewModel.getAllMovies().getValue();
                movieAdapter = new MovieAdapter(movies, MainActivity.this);
                recyclerView.setAdapter(movieAdapter);
                recyclerView.getAdapter().notifyDataSetChanged();
                currentSort = 2;
                break;


        }

        return true;
    }

    public void display(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
