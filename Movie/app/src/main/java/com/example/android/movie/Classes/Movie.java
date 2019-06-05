package com.example.android.movie.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    private int id;
    private String title;
    private String imageUrl;
    private String plot;
    private String releaseDate;
    private float rating;


    @Ignore
    private boolean isFavorite = false;



    public Movie(int id, String title, String imageUrl, String plot, String releaseDate, float rating) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.plot = plot;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    @Ignore
    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        plot = in.readString();
        releaseDate = in.readString();
        rating = in.readFloat();
        isFavorite = in.readByte() != 0;
    }


    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlot() {
        return plot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }

    @Ignore
    @Override
    public String toString() {
        return title + "\n\n" + imageUrl + "\n\n" + plot + "\n\n" + releaseDate + "\n\n" + rating;
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(plot);
        dest.writeString(releaseDate);
        dest.writeFloat(rating);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
