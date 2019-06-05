package com.example.android.movie.Foreground;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.movie.Data.MovieRepository;
import com.example.android.movie.Classes.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    LiveData<List<Movie>> allMovies;
    MovieRepository movieRepository;

    MutableLiveData<List<Movie>> mutableLiveData;


    public MovieViewModel(Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAllMovies();

    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public void update(Movie movie) {
        movieRepository.update(movie);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }


}
