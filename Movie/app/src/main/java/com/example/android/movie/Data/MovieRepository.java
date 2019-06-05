package com.example.android.movie.Data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android.movie.Classes.Movie;

import java.util.List;

public class MovieRepository {
    private Database db;
    private LiveData<List<Movie>> allMovies;
    private MovieDao movieDao;

    public MovieRepository(Context context) {
        db = Database.getDataBase(context);
        movieDao = db.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    public void insert(Movie movie) {
        new InsertTask(movieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteTask(movieDao).execute(movie);
    }

    public void update(Movie movie) {
        new UpdateTask(movieDao).execute(movie);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    private static class InsertTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        InsertTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insertMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Movie, Void, Void> {
        MovieDao movieDao;

        DeleteTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteMovie(movies[0]);
            return null;
        }
    }


    private static class UpdateTask extends AsyncTask<Movie, Void, Void> {
        MovieDao movieDao;

        UpdateTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.updateMovie(movies[0]);
            return null;
        }
    }
}
