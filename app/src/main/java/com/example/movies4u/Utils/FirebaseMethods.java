package com.example.movies4u.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movies4u.Models.User;
import com.example.movies4u.Models.UserAccountSettings;
import com.example.movies4u.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {
    private static final String TAG="FirebaseMethods";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context){
        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        mContext =context;

        if(mAuth.getCurrentUser() !=null){
            userID =mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot){
        User user =new User();
        //Getting error in the below line
        for (DataSnapshot ds: dataSnapshot.child(mContext.getString(R.string.dbname_users)).getChildren()){
            user.setUsername(ds.getValue(User.class).getUsername());
            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Registering a new email and password
     * @param username
     * @param email
     * @param password
     */
    public void registerNewEmail(final String username,final String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(mContext, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                        else if(task.isSuccessful()){
                            userID=mAuth.getCurrentUser().getUid();
                        }
                    }
                });
    }

    public void addNewUser(String curr_userId,String email, String username){
        User user=new User(email,StringManipulation.condenseUsername(username));
        userID=curr_userId;
        if(userID!=null)
        {
            myRef.child(mContext.getString(R.string.dbname_users))
                    .child(userID)
                    .setValue(user);

            UserAccountSettings settings = new UserAccountSettings(StringManipulation.condenseUsername(username), username);

            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .setValue(settings);
        }
        else {
            Log.d(TAG, "user is null");
        }


    }


}
