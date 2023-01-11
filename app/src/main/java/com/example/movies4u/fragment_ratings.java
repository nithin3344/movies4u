package com.example.movies4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_ratings extends Fragment {
    public static final String TAG="fragment_ratings";
    RecyclerView recyclerView;
    Ratings_Adapter Ratings_Adapter;
    ImageView mComment;
    Context mContext;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ratings, container, false);
        mComment= (ImageView) view.findViewById(R.id.speech_bubble);
        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        FirebaseRecyclerOptions<Ratings_model> options =
                new FirebaseRecyclerOptions.Builder<Ratings_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ratings"), Ratings_model.class)
                        .build();

        mContext=getContext();
        Ratings_Adapter = new Ratings_Adapter(options,getActivity());
        recyclerView.setAdapter(Ratings_Adapter);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Ratings_Adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        Ratings_Adapter.startListening();
    }

}