package com.example.movies4u;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movies4u.databinding.ActivityMainBinding;
import com.example.movies4u.fragments.MoviesListFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, MoviesListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}