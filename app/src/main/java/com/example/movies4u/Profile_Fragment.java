package com.example.movies4u;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Profile_Fragment extends Fragment {
    Activity loginactivity;
    private Button button1;
    private Button button2;
    private TextView user;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginactivity =getActivity();
        View view =inflater.inflate(R.layout.fragment_profile_, container, false);
        button1=view.findViewById(R.id.btnChangePassword);
        button2=view.findViewById(R.id.btnLogout);
        user=view.findViewById(R.id.user);
        String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
        String display_user=(Character.toUpperCase(userEmail.charAt(0)))+userEmail.substring(1);
        user.setText(display_user);
        button1.setOnClickListener(view12 -> {
            mFirebaseAuth.sendPasswordResetEmail(userEmail);
            Toast.makeText(loginactivity, "Reset Link sent to registered email", Toast.LENGTH_SHORT).show();
        });
        button2.setOnClickListener(view1 -> {
            mFirebaseAuth.signOut();
            Intent loginintent =new Intent(getContext(), login_activity.class);
            startActivity(loginintent);
            getActivity().finish();
        });

        return view;
    }
}