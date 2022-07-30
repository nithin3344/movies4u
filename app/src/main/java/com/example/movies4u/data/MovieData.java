package com.example.movies4u.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieData {
    @SerializedName("title")
    public String movieName;

    @SerializedName("overview")
    public String overView;

    @SerializedName("poster_path")
    public String poster;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("genre_ids")
    public List<Integer> genreIDs;
}
