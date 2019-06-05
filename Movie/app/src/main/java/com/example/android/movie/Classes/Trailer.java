package com.example.android.movie.Classes;

import com.example.android.movie.R;

public class Trailer {

    private String title;
    private String videoUrlKey;

    public String getVideoUrlKey() {
        return videoUrlKey;
    }

    public void setVideoUrlKey(String videoUrlKey) {
        this.videoUrlKey = videoUrlKey;
    }

    private int imgId = R.drawable.play_btn;

    Trailer() {

    }

    public Trailer(String title, String videoUrlKey) {
        this.title = title;
        this.videoUrlKey = videoUrlKey;
        //this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
