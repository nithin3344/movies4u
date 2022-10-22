package com.example.movies4u;

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


public class Explore_Fragment extends Fragment {


    RecyclerView recyclerView;
    MainpostsAdapter mainpostsAdapter;
    ProgressBar pb;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        pb = view.findViewById(R.id.explorePB);
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