package com.example.android.movie.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.movie.Classes.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("delete from Movie where id = :id")
    void deleteMovieById(int id);

    @Update
    void updateMovie(Movie movie);


    @Query("select * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("select * from Movie where Id = :id")
    Movie getMovie(int id);


}
