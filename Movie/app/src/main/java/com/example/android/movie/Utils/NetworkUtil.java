package com.example.android.movie.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {


    static String[] sorting = {"popular", "top_rated"};
    static private String movieID = "";

    //TODO Put your API Key here.
    static String apiKey = "";

    static String BASE_URL = "";
    static String YOUTUBE_URL = "https://www.youtube.com/watch?v=JLwW5HivZg4";
    static String TRAILERS_URL = "";
    static String REVIEWS_URL = "http://api.themoviedb.org/3/movie/83542/reviews?api_key=" + apiKey;
    static String REVIEW_URL = "http://api.themoviedb.org/3/review/51910979760ee320eb020fc2?api_key=" + apiKey;


    public static String getHttpRespnse(int requestCode) throws IOException {

        switch (requestCode) {

            case 0:
                BASE_URL = "https://api.themoviedb.org/3/movie/" + sorting[requestCode] + "?api_key=" + apiKey + "&include_adult=true&language=en-US&page=1";
                break;

            case 1:
                BASE_URL = "https://api.themoviedb.org/3/movie/" + sorting[requestCode] + "?api_key=" + apiKey + "&include_adult=true&language=en-US&page=1";
                break;

            case 2:
                BASE_URL = "http://api.themoviedb.org/3/movie/" + getMvieID() + "/videos?api_key=" + apiKey;
                ;
                break;

            case 3:
                BASE_URL = "http://api.themoviedb.org/3/movie/" + getMvieID() + "/reviews?api_key=" + apiKey;
                break;

        }


        URL url = null;
        try {
            url = new URL(BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream in = http.getInputStream();
        Scanner sc = new Scanner(in);
        sc.useDelimiter("\\A");
        boolean hasNext = sc.hasNext();
        if (hasNext) {
            return sc.next();
        } else {
            return null;
        }


    }

    public static void setMovieID(int id) {
        movieID = String.valueOf(id);
    }

    public static String getMvieID() {
        return movieID;
    }



}
