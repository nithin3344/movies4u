package com.example.movies4u.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies4u.R;
import com.example.movies4u.data.MovieData;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public final List<MovieData> list;

    public MoviesAdapter(List<MovieData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_layout, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieData modelMovie = list.get(position);
        holder.movieName.setText(modelMovie.movieName);
        holder.releaseYear.setText(modelMovie.releaseDate.substring(0, 4));
        Glide.with(holder.moviePoster.getContext())
                .load("https://image.tmdb.org/t/p/original" + modelMovie.poster)
                .fitCenter()
                .into(holder.moviePoster);
        Log.d("RVData",modelMovie.toString());
        holder.movieCard.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "Clicked on "+modelMovie.movieName,Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView moviePoster;
        private final TextView movieName;
        private final TextView releaseYear;
        private final CardView movieCard;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movieIcon);
            movieName = itemView.findViewById(R.id.movieName);
            releaseYear = itemView.findViewById(R.id.releaseYear);
            movieCard = itemView.findViewById(R.id.movieCard);
        }
    }
}
