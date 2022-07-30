package com.example.movies4u.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies4u.R;
import com.example.movies4u.databinding.FragmentMovieDetailBinding;

public class MovieDetailFragment extends Fragment {
    FragmentMovieDetailBinding binding;
    public MovieDetailFragment() {
        // Required empty public constructor
    }
    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}