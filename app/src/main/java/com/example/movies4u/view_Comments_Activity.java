package com.example.movies4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class view_Comments_Activity extends AppCompatActivity {

    comments_Adapter comments_Adapter;
    EditText commentText;
    ImageView commentSubmit, backArrow;
    DatabaseReference userref, commentref,postref;
    String postkey;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        commentText=(EditText)findViewById(R.id.set_comment);
        commentSubmit=(ImageView)findViewById(R.id.ivPostComment);
        backArrow=(ImageView) findViewById(R.id.backArrow);
        recyclerView=(RecyclerView)findViewById(R.id.comment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postkey=getIntent().getStringExtra("postkey");
        userref= FirebaseDatabase.getInstance().getReference().child("user_account_settings");
        commentref=FirebaseDatabase.getInstance().getReference().child("ratings").child(postkey).child("comments");
        postref=FirebaseDatabase.getInstance().getReference().child("ratings").child(postkey);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId=user.getUid();

        FirebaseRecyclerOptions<comment_model> options=
                new FirebaseRecyclerOptions.Builder<comment_model>()
                        .setQuery(commentref, comment_model.class)
                        .build();
        comments_Adapter = new comments_Adapter(options,this);
        recyclerView.setAdapter(comments_Adapter);
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentpost=commentText.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(commentText.getWindowToken(), 0);
                commentText.setText("");
                userref.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String displayname=snapshot.child("display_name").getValue().toString();
                            processcomment(displayname,commentpost);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void processcomment(String username,String commentpost)
            {
                String randompostkey=userId+""+new Random().nextInt(1000);

                Calendar datevalue=Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yy");
                String cdate=dateFormat.format(datevalue.getTime());

                SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
                String ctime=timeFormat.format(datevalue.getTime());

                HashMap cmnt=new HashMap();
                cmnt.put("uid",userId);
                cmnt.put("username",username);
                cmnt.put("usermsg",commentpost);
                cmnt.put("date",cdate);
                cmnt.put("time",ctime);

                commentref.child(randompostkey).updateChildren(cmnt)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Comment Added",Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(getApplicationContext(),task.toString(),Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        comments_Adapter.startListening();
    }
}