package com.example.android.movie.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movie.Classes.Trailer;
import com.example.android.movie.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    List<Trailer> trailers;


    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public TrailerAdapter(List<Trailer> trailers) {

        this.trailers = trailers;
    }


    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        TrailerHolder trailerHolder = new TrailerHolder(view);
        return trailerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder trailerHolder, int i) {
        trailerHolder.trailerTitle.setText(trailers.get(i).getTitle());
        trailerHolder.playButton.setImageResource(trailers.get(i).getImgId());
    }


    @Override
    public int getItemCount() {
        return trailers.size();
    }


    class TrailerHolder extends RecyclerView.ViewHolder {
        TextView trailerTitle;
        ImageView playButton;

        TrailerHolder(final View itemView) {
            super(itemView);

            trailerTitle = itemView.findViewById(R.id.trailer_text);
            playButton = itemView.findViewById(R.id.img_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailers.get(getAdapterPosition()).getVideoUrlKey()));
                    itemView.getContext().startActivity(intent);
                }
            });

        }

    }
}
