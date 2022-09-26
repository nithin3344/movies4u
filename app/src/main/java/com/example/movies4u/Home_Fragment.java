package com.example.movies4u;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Home_Fragment extends Fragment {


    RecyclerView recyclerView;
    MainpostsAdapter mainpostsAdapter;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_, container, false);
        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MainpostsModel> options =
                new FirebaseRecyclerOptions.Builder<MainpostsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), MainpostsModel.class)
                        .build();

//        Log.d("text", FirebaseDatabase.getInstance().getReference().child("Posts").child("post1").toString());
        mainpostsAdapter = new MainpostsAdapter(options);
        recyclerView.setAdapter(mainpostsAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainpostsAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        mainpostsAdapter.startListening();
    }
}