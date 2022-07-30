package com.example.movies4u;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movies4u.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class activity_register extends AppCompatActivity {
    FirebaseAuth mAuth;
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.HaveAccount.setOnClickListener(view -> {
            Intent loginintent=new Intent(activity_register.this,login_activity.class);
            startActivity(loginintent);
        });
        mAuth=FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(view -> ValidateDataandDoRegister());
    }

    private void ValidateDataandDoRegister() {
        String email=binding.inputEmail.getText().toString().trim();
        String password=binding.inputPassword.getText().toString().trim();
        String confirmPassword=binding.inputConfirmPassword.getText().toString().trim();
        if(email.isEmpty()){
            binding.inputEmail.setError("Enter Email Address");
            binding.inputEmail.requestFocus();
        }
        else if(email.length()<10 || !email.contains("@")){
            binding.inputEmail.setError("Enter valid Email");
            binding.inputEmail.requestFocus();
        }
        else if(password.isEmpty()){
            binding.inputPassword.setError("Enter the password");
            binding.inputPassword.requestFocus();
        }
        else if(password.length()<8){
            binding.inputPassword.setError("Password should be greater than 8 characters");
            binding.inputPassword.requestFocus();
        }
        else if(confirmPassword.isEmpty()){
            binding.inputConfirmPassword.setError("Re-Enter the Password");
            binding.inputConfirmPassword.requestFocus();
        }
        else if(confirmPassword.length()<8){
            binding.inputConfirmPassword.setError("Password should be greater than 8 characters");
            binding.inputConfirmPassword.requestFocus();
        }
        else if(!password.equals(confirmPassword)){
            binding.inputPassword.setError("Password not matched");
            binding.inputPassword.requestFocus();
            binding.inputConfirmPassword.setError("Password not matched");
            binding.inputConfirmPassword.requestFocus();
            binding.inputPassword.setText("");
            binding.inputConfirmPassword.setText("");
        }
        else{
            doRegister(email,password);
        }
    }

    private void doRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                sendVerificationEmail();
            }

        }).addOnFailureListener(e -> {
            if(e instanceof FirebaseAuthUserCollisionException){
                binding.btnRegister.setEnabled(true);
                binding.inputEmail.setError("Email Already Registered");
                binding.inputEmail.requestFocus();
            }
            else{
                binding.btnRegister.setEnabled(true);
                Toast.makeText(activity_register.this, "Oops! Something Went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    binding.btnRegister.setEnabled(true);
                    Toast.makeText(activity_register.this, "Email has been sent to your email address", Toast.LENGTH_SHORT).show();
                }
                else {
                    binding.btnRegister.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Oops! failed to send verification email",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}