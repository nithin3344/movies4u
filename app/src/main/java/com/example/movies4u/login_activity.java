package com.example.movies4u;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies4u.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.dontHaveAccount.setOnClickListener(view -> {
            Intent registerintent = new Intent(login_activity.this, activity_register.class);
            startActivity(registerintent);
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        binding.btnlogin.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(binding.inputEmail.getText()) && !TextUtils.isEmpty(binding.inputPassword.getText())) {
                auth.signInWithEmailAndPassword(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if ((auth.getCurrentUser()).isEmailVerified()) {
                            startActivity(new Intent(login_activity.this, MainActivity.class));
                            finish();
                        } else
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> Toast.makeText(login_activity.this, "Verification email sent", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(login_activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                });

            } else {
                Toast.makeText(login_activity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}