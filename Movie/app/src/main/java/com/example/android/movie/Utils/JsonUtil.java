package com.example.android.movie.Utils;

        import com.example.android.movie.Classes.Movie;
        import com.example.android.movie.Classes.Review;
        import com.example.android.movie.Classes.Trailer;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class JsonUtil {


    public static List<Movie> jsonObjExtractorMovie(String json) {
        List<Movie> movies = new ArrayList<Movie>();

        JSONObject rootObj = null;
        JSONArray results = null;
        JSONObject jsonMovie = null;

        try {
            rootObj = new JSONObject(json);
            results = rootObj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                int Id;
                String title, image, plot, releaseDate;
                float rating;

                Id = results.getJSONObject(i).getInt("id");
                title = results.getJSONObject(i).getString("title");
                image = results.getJSONObject(i).getString("poster_path");
                plot = results.getJSONObject(i).getString("overview");
                releaseDate = results.getJSONObject(i).getString("release_date");
                rating = (float) results.getJSONObject(i).getDouble("vote_average");

                movies.add(new Movie(Id, title, image, plot, releaseDate, rating));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }


    public static List<Trailer> jsonObjExtractorTrailers(String json) {
        List<Trailer> trailers = new ArrayList<Trailer>();

        JSONObject rootObj = null;
        JSONArray results = null;


        try {
            rootObj = new JSONObject(json);
            results = rootObj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                String title, videoUrlKey;


                title = results.getJSONObject(i).getString("name");
                videoUrlKey = results.getJSONObject(i).getString("key");


                trailers.add(new Trailer(title, videoUrlKey));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }

    public static List<Review> jsonObjExtractorReview(String json) {
        List<Review> reviews = new ArrayList<Review>();

        JSONObject rootObj = null;
        JSONArray results = null;


        try {
            rootObj = new JSONObject(json);
            results = rootObj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                String author, body;


                author = results.getJSONObject(i).getString("author");
                body = results.getJSONObject(i).getString("content");


                reviews.add(new Review(author, body));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
