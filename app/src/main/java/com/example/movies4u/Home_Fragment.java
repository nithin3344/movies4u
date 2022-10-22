package com.example.movies4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Home_Fragment extends Fragment {
    CardView cardsuggestions;
    CardView cardott;
    CardView cardratings;
    CardView cardyoutube;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_, container, false);

        cardsuggestions=(CardView) view.findViewById(R.id.cardsuggestions);
        cardott=(CardView) view.findViewById(R.id.cardott);
        cardratings=(CardView) view.findViewById(R.id.cardratings);
        cardyoutube=(CardView) view.findViewById(R.id.cardyoutube);

        cardsuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragnent(new fragment_suggestions());
            }
        });

        cardott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_toast("you clicked on ott tab ");
            }
        });

        cardratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_toast("you clicked on ratings tab ");
            }
        });

        cardyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_toast("you clicked on youtube tab ");
            }
        });

        return view;
    }
    private void show_toast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    private void replaceFragnent(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        //        fragmentTransaction.addToBackStack()
    }
}