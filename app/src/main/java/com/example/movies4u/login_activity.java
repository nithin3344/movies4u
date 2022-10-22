package com.example.movies4u;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    TextView dontHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        dontHaveAccount = findViewById(R.id.dontHaveAccount);
        ProgressBar pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.GONE);
        dontHaveAccount.setOnClickListener(view -> {
            Intent registerintent = new Intent(login_activity.this, activity_register.class);
            startActivity(registerintent);
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnlogin);
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(login_activity.this, MainActivity.class));
            finish();
        }
        btnLogin.setOnClickListener(view -> {
            // dismiss keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPassword.getWindowToken(), 0);

            pb.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(inputEmail.getText()) && !TextUtils.isEmpty(inputPassword.getText())) {
                auth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString()).addOnSuccessListener(authResult -> {
                    if ((auth.getCurrentUser()).isEmailVerified()) {
                        pb.setVisibility(View.GONE);
                        startActivity(new Intent(login_activity.this, MainActivity.class));
                        finish();
                    } else
                        auth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(task -> {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(login_activity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> Toast.makeText(login_activity.this, "something went wrong", Toast.LENGTH_SHORT).show());
                }).addOnFailureListener(e -> {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(login_activity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                });

            } else {
                pb.setVisibility(View.GONE);
                Toast.makeText(login_activity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}