package com.example.movies4u;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Hide status Bar
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        Button button1=findViewById(R.id.button2);
        button.setOnClickListener(view -> {
            Intent registerintent=new Intent(MainActivity.this,activity_register.class);
            startActivity(registerintent);
        });
        button1.setOnClickListener(view -> {
            Intent logininten=new Intent(MainActivity.this,login_activity.class);
            startActivity(logininten);
        });
    }
}