package com.example.movies4u;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login_activity extends AppCompatActivity {
    TextView dontHaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        dontHaveAccount= (TextView) findViewById(R.id.dontHaveAccount);
        dontHaveAccount.setOnClickListener(view -> {
            Intent registerintent=new Intent(login_activity.this,activity_register.class);
            startActivity(registerintent);
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(inputEmail.getText()) && !TextUtils.isEmpty(inputPassword.getText())){
                auth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if((auth.getCurrentUser()).isEmailVerified()){
                            startActivity(new Intent(login_activity.this,MainActivity.class));
                            finish();
                        }
                        else auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> Toast.makeText(login_activity.this, "Verification email sent", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login_activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }else{
                Toast.makeText(login_activity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}