package com.example.android.movie.Data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.movie.Classes.Movie;


@android.arch.persistence.room.Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static String nameDB = "movie_db";
    private static Database db;

    public synchronized static Database getDataBase(Context context) {

        if (db == null) {
            return db = Room.databaseBuilder(context, Database.class, nameDB).fallbackToDestructiveMigration().build();
        }

        return db;

    }

    public abstract MovieDao movieDao();
}
