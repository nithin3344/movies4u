package com.example.movies4u;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_YoutubeVideos extends Fragment {

    RecyclerView recyclerView;
    YoutubeVideos_adapter youtubeVideos_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_youtubevideos, container, false);

        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<YoutubeVideos_Model> options =
                new FirebaseRecyclerOptions.Builder<YoutubeVideos_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("new_videos"), YoutubeVideos_Model.class)
                        .build();

        youtubeVideos_adapter = new YoutubeVideos_adapter(options);
        recyclerView.setAdapter(youtubeVideos_adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        youtubeVideos_adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        youtubeVideos_adapter.startListening();
    }
}