package com.example.android.movie.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movie.Classes.Review;
import com.example.android.movie.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        ReviewHolder reviewHolder = new ReviewHolder(view);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int i) {
        reviewHolder.author.setText(reviews.get(i).getAuthor());
        reviewHolder.body.setText(reviews.get(i).getBody());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView body;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author_txt);
            body = itemView.findViewById(R.id.body_txt);
        }
    }
}
