package com.example.movies4u.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootCallData {
    @SerializedName("results")
    public ArrayList<MovieData>movies;
}
