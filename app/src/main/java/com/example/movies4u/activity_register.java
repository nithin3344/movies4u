package com.example.movies4u;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies4u.Models.User;
import com.example.movies4u.Models.UserAccountSettings;
import com.example.movies4u.Utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_register extends AppCompatActivity {
    TextView HaveAccount;
    EditText inputUsername,inputEmail,inputPassword,inputConfirmPassword;
    String username,email,password;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressBar pb;
    private String userID;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Hide status Bar
        setContentView(R.layout.activity_register);
        firebaseMethods =new FirebaseMethods(activity_register.this);

        HaveAccount= (TextView) findViewById(R.id.HaveAccount);
        pb = findViewById(R.id.progressBar3);
        inputUsername=findViewById(R.id.inputUsername);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        username=inputUsername.getText().toString();
        email=inputEmail.getText().toString();
        password=inputPassword.getText().toString();

        pb.setVisibility(View.GONE);
        HaveAccount.setOnClickListener(view -> {
            Intent loginintent=new Intent(activity_register.this,login_activity.class);
            startActivity(loginintent);
        });

        mAuth=FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> ValidateDataandDoRegister());
    }

    private void ValidateDataandDoRegister() {
        String username=inputUsername.getText().toString().trim();
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();
        String confirmPassword=inputConfirmPassword.getText().toString().trim();
        if(email.isEmpty()){
            inputEmail.setError("Enter Email Address");
            inputEmail.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(email.length()<10){
            inputEmail.setError("Enter valid Email");
            inputEmail.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(password.isEmpty()){
            inputPassword.setError("Enter the password");
            inputPassword.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(password.length()<8){
            inputPassword.setError("Password should be greater than 8 characters");
            inputPassword.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(confirmPassword.isEmpty()){
            inputConfirmPassword.setError("Re-Enter the Password");
            inputConfirmPassword.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(confirmPassword.length()<8){
            inputConfirmPassword.setError("Password should be greater than 8 characters");
            inputConfirmPassword.requestFocus();
            pb.setVisibility(View.GONE);
        }
        else if(!password.equals(confirmPassword)){
            inputPassword.setError("Password not matched");
            inputPassword.requestFocus();
            inputConfirmPassword.setError("Password not matched");
            inputConfirmPassword.requestFocus();
            inputPassword.setText("");
            inputConfirmPassword.setText("");
            pb.setVisibility(View.GONE);
        }
        else{
            pb.setVisibility(View.VISIBLE);
            doRegister(email,password);
        }
    }

    private void doRegister(String email, String password) {
        pb.setVisibility(View.VISIBLE);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(task -> {
            if(task != null ){
                if(mAuth.getCurrentUser() != null){
                    userID=mAuth.getCurrentUser().getUid();
                }
                setupFirebaseAuth();
                mAuth.addAuthStateListener(mAuthListener);
                sendVerificationEmail();
            }

        }).addOnFailureListener(e -> {
            if(e instanceof FirebaseAuthUserCollisionException){
                btnRegister.setEnabled(true);
                inputEmail.setError("Email Already Registered");
                pb.setVisibility(View.GONE);
                inputEmail.requestFocus();
            }
            else{
                btnRegister.setEnabled(true);
                pb.setVisibility(View.GONE);
                Toast.makeText(activity_register.this, "Oops! Something Went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationEmail() {
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
            if(task != null && task.isSuccessful()){
                btnRegister.setEnabled(true);
                pb.setVisibility(View.GONE);
                Toast.makeText(activity_register.this, "Verification Email has been sent to your email address", Toast.LENGTH_SHORT).show();
                //fixing the bug
                mAuth.signOut();
            }
            else {
                btnRegister.setEnabled(true);
                pb.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Oops! failed to send verification email",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        //cannot get into this part of code
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //1st check: Make sure the username is not already in use
                            username=inputUsername.getText().toString();
                            if(firebaseMethods.checkIfUsernameExists(username, dataSnapshot)){
                                //to randomly generate key to make sure username is unique
                                append = myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                            }
                            username = username + append;
                            append="";
                            //add new user to the database
                            Log.d(TAG, "adding new user to database with user id"+userID);
                            email=inputEmail.getText().toString();
                            firebaseMethods.addNewUser(userID,email, username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG,"setup fire base unsuccessful"+databaseError);
                        }
                    });
                    finish();



                } else {
                    // User is signed out
                    Log.d(TAG, "User signed out setup fire base unsuccessful "+"onAuthStateChanged:signed _out");
                }
                // ...
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}