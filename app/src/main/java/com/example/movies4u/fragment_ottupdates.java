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

public class fragment_ottupdates extends Fragment {

    RecyclerView recyclerView;
    ottupdates_Adapter ottupdates_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ottupdates, container, false);

        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<ottupdates_Model> options =
                new FirebaseRecyclerOptions.Builder<ottupdates_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ott_updates"), ottupdates_Model.class)
                        .build();

        ottupdates_adapter = new ottupdates_Adapter(options);
        recyclerView.setAdapter(ottupdates_adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        ottupdates_adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        ottupdates_adapter.startListening();
    }
}