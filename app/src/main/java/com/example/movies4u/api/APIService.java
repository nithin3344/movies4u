package com.example.movies4u.api;

import com.example.movies4u.data.RootCallData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET ("/3/movie/popular/")
    Call<RootCallData> getPopularMovies(@Query("api_key")String apiKey);

    @GET ("/3/movie/upcoming/")
    Call<RootCallData> getUpcomingMovies(@Query("api_key")String apiKey);

    @GET ("/3/movie/now_playing/")
    Call<RootCallData> getRecentMovies(@Query("api_key")String apiKey);
}
