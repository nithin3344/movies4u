package com.example.movies4u;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Settings_Fragment extends Fragment {
    Button instagram,youtube,twitter,feedback;
    TextView textViewinsta,textViewyt,textViewtwitter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_settings_, container, false);
        instagram=(Button) view.findViewById(R.id.instagram);
        youtube=(Button) view.findViewById(R.id.youtube);
        twitter=(Button) view.findViewById(R.id.twitter);
        feedback=(Button) view.findViewById(R.id.feedback);
        textViewinsta= (TextView) view.findViewById(R.id.textView3);
        textViewyt= (TextView) view.findViewById(R.id.textView4);
        textViewtwitter= (TextView) view.findViewById(R.id.textView5);


        instagram.setOnClickListener(view1 -> {
            Uri insta = Uri.parse("https://www.instagram.com/movies4u_official/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, insta);
            startActivity(intent);
        });

        textViewinsta.setOnClickListener(view1 -> {
            Uri insta = Uri.parse("https://www.instagram.com/movies4u_official/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, insta);
            startActivity(intent);
        });

        youtube.setOnClickListener(view1 -> {
            Uri yt = Uri.parse("https://www.youtube.com/c/Movies4uOfficial"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, yt);
            startActivity(intent);
        });

        textViewyt.setOnClickListener(view1 -> {
            Uri yt = Uri.parse("https://www.youtube.com/c/Movies4uOfficial"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, yt);
            startActivity(intent);
        });

        twitter.setOnClickListener(view1 -> {
            Uri twitter = Uri.parse("https://twitter.com/movies4u_officl"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, twitter);
            startActivity(intent);
        });

        textViewtwitter.setOnClickListener(view1 -> {
            Uri twitter = Uri.parse("https://twitter.com/movies4u_officl"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, twitter);
            startActivity(intent);
        });

        feedback.setOnClickListener(view1 -> {
            Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
            feedbackEmail.setType("text/email");
            feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"nithinpogalla123@gmail.com"});
            feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
        });
        return view;
    }
}