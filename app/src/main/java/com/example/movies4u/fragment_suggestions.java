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

public class fragment_suggestions extends Fragment {

    RecyclerView recyclerView;
    Suggestions_Adapter Suggestions_Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_suggestions, container, false);

        recyclerView =(RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<Suggestions_model> options =
                new FirebaseRecyclerOptions.Builder<Suggestions_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Suggestions"), Suggestions_model.class)
                        .build();

        Suggestions_Adapter = new Suggestions_Adapter(options);
        recyclerView.setAdapter(Suggestions_Adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Suggestions_Adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        Suggestions_Adapter.startListening();
    }
}