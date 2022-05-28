package com.example.movies4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class activity_register extends AppCompatActivity {
    TextView HaveAccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Hide status Bar
        setContentView(R.layout.activity_register);
        HaveAccount= (TextView) findViewById(R.id.HaveAccount);
        HaveAccount.setOnClickListener(view -> {
            Intent loginintent=new Intent(activity_register.this,login_activity.class);
            startActivity(loginintent);
        });
        
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);

        mAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ValidateDataandDoRegister();
            }
        });
    }

    private void ValidateDataandDoRegister() {
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();
        String confirmPassword=inputConfirmPassword.getText().toString().trim();
        if(email.isEmpty()){
            inputEmail.setError("Enter Email Address");
            inputEmail.requestFocus();
        }
        else if(email.length()<10){
            inputEmail.setError("Enter valid Email");
            inputEmail.requestFocus();
        }
        else if(password.isEmpty()){
            inputPassword.setError("Enter the password");
            inputPassword.requestFocus();
        }
        else if(password.length()<8){
            inputPassword.setError("Password should be greater than 8 characters");
            inputPassword.requestFocus();
        }
        else if(confirmPassword.isEmpty()){
            inputConfirmPassword.setError("Re-Enter the Password");
            inputConfirmPassword.requestFocus();
        }
        else if(confirmPassword.length()<8){
            inputConfirmPassword.setError("Password should be greater than 8 characters");
            inputConfirmPassword.requestFocus();
        }
        else if(!password.equals(confirmPassword)){
            inputPassword.setError("Password not matched");
            inputPassword.requestFocus();
            inputConfirmPassword.setError("Password not matched");
            inputConfirmPassword.requestFocus();
            inputPassword.setText("");
            inputConfirmPassword.setText("");
        }
        else{
            doRegister(email,password);
        }
    }

    private void doRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendVerificationEmail();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){
                    btnRegister.setEnabled(true);
                    inputEmail.setError("Email Already Registered");
                    inputEmail.requestFocus();
                }
                else{
                    btnRegister.setEnabled(true);
                    Toast.makeText(activity_register.this, "Oops! Something Went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationEmail() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        btnRegister.setEnabled(true);
                        Toast.makeText(activity_register.this, "Email has been sent to your email address", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRegister.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Oops! failed to send verification email",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}