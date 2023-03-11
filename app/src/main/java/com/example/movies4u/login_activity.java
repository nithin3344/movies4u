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
    FirebaseAuth mAuth;
    TextView dontHaveAccount;
    ProgressBar pb;
    EditText inputEmail;
    EditText inputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        dontHaveAccount = findViewById(R.id.dontHaveAccount);
        pb = findViewById(R.id.progressBar);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);

        pb.setVisibility(View.GONE);
        dontHaveAccount.setOnClickListener(view -> {
            Intent registerintent = new Intent(login_activity.this, activity_register.class);
            startActivity(registerintent);
        });
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(login_activity.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(view -> {
            // dismiss keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPassword.getWindowToken(), 0);
            if (!TextUtils.isEmpty(inputEmail.getText()) && !TextUtils.isEmpty(inputPassword.getText())) {
                pb.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString()).
                        addOnSuccessListener(authResult -> {
                            if ((mAuth.getCurrentUser()).isEmailVerified()) {
                                pb.setVisibility(View.GONE);
                                startActivity(new Intent(login_activity.this, MainActivity.class));
                                finish();
                            } else if(!mAuth.getCurrentUser().isEmailVerified()){
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
                                            //bug fix
                                            mAuth.signOut();
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(login_activity.this, "Please Verify the email sent your email address", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(login_activity.this, "something went wrong",
                                                Toast.LENGTH_SHORT).show());
                            }
                        }).addOnFailureListener(e -> {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(login_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });

            } else {
                pb.setVisibility(View.GONE);
                Toast.makeText(login_activity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}