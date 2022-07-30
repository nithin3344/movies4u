package com.example.movies4u.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies4u.adapters.MoviesAdapter;
import com.example.movies4u.api.APIService;
import com.example.movies4u.data.MovieData;
import com.example.movies4u.data.RootCallData;
import com.example.movies4u.databinding.FragmentMoviesListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesListFragment extends Fragment {
    FragmentMoviesListBinding binding;
    MoviesAdapter adapter;
    private static final String BASE_URL = "https://api.themoviedb.org/";

    public MoviesListFragment() {
        // Required empty public constructor
    }


    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<RootCallData> popularMovies = service.getPopularMovies("5b064f32c21a136e1382810909b57106");
        Call<RootCallData> recentMovies = service.getRecentMovies("5b064f32c21a136e1382810909b57106");
        Call<RootCallData> upcomingMovies = service.getUpcomingMovies("5b064f32c21a136e1382810909b57106");
        parseIntoRV(popularMovies,binding.popularRV);
        parseIntoRV(recentMovies,binding.RecentRV);
        parseIntoRV(upcomingMovies, binding.UpComingRV);
        return binding.getRoot();
    }

    public void parseIntoRV(Call<RootCallData> rawData, RecyclerView rv) {
        final List<MovieData>[] list = new List[]{new ArrayList<>()};
        rv.setLayoutManager(new LinearLayoutManager(getActivity()
                , LinearLayoutManager.HORIZONTAL
                , false));
        rawData.enqueue(new Callback<RootCallData>() {
            @Override
            public void onResponse(Call<RootCallData> call, Response<RootCallData> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("TestData", response.body().movies.get(0).movieName);
                        list[0] = response.body().movies;
                        adapter = new MoviesAdapter(list[0]);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RootCallData> call, Throwable t) {
                Log.d("TestError", t.getMessage());
            }
        });
    }
}