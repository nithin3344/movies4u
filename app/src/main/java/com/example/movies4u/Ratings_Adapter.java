package com.example.movies4u;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ratings_Adapter extends FirebaseRecyclerAdapter<Ratings_model, Ratings_Adapter.myViewHolder> {
    String TAG = "rating_adapter";
    ProgressBar progressBar;
    Ratings_Adapter ratings_adapter;
    FirebaseUser firebaseUser;
    public Context mContext;
    DataSnapshot tempDataSnapShot;

    public Ratings_Adapter(@NonNull FirebaseRecyclerOptions<Ratings_model> options,Context context) {
        super(options);
        this.mContext=context;
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Ratings_model model) {
        final String postKey=getRef(position).getKey();
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().
                child("ratings").child(postKey);
        final long[] size = {0};
        ref.child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                size[0] = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        tempDataSnapShot = ref.child("comments").get().getResult();
//        size[0] = tempDataSnapShot.getChildrenCount();
//        holder.viewComment.setText("View all "+ size[0]+" Comments");

        int timestampdifference = (int) getTimestampDifference(model.getTimestamp());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (timestampdifference < 60) {
            if (timestampdifference == 1) {
                holder.timestamp.setText(String.valueOf(timestampdifference) + " minute ago");
            } else
                holder.timestamp.setText(String.valueOf(timestampdifference) + " minutes ago");
        } else if (timestampdifference >= 60 && timestampdifference < 1440) {
            if (timestampdifference / 60 == 1) {
                holder.timestamp.setText(String.valueOf(timestampdifference / 60) + " hour ago");
            } else
                holder.timestamp.setText(String.valueOf(timestampdifference / 60) + " hours ago");
        } else {
            if (timestampdifference / 60 / 24 == 1) {
                holder.timestamp.setText(String.valueOf(timestampdifference / 60 / 24) + " day ago");
            } else
                holder.timestamp.setText(String.valueOf(timestampdifference / 60 / 24) + " days ago");
        }

        Glide.with(holder.img.getContext())
                .load(model.getImgurl())
                .placeholder(R.drawable.movie_icon)
                .error(R.drawable.cannot_load_item)
                .into(holder.img);

//        getComments("rating" + String.valueOf(holder.getBindingAdapterPosition() + 1), holder.viewComment);

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),view_Comments_Activity.class);
                intent.putExtra("postkey", postKey);
                mContext.startActivity(intent);
            }
        });

        holder.viewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),view_Comments_Activity.class);
                intent.putExtra("postkey", postKey);
                mContext.startActivity(intent);
            }
        });



    }

    private float getTimestampDifference(String time) {
        float difference;
        Long currenttime = (System.currentTimeMillis());
        try {
            difference = (Math.round((currenttime - Long.valueOf(time)) / 1000 / 60));
        } catch (Exception e) {
            Log.e(TAG, "GetTimestampdifference:  " + time + " " + e.getMessage());
            difference = 0;
        }
        return difference;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myViewHolder view_holder = new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ratings_item, parent, false));
//        return new myViewHolder(view);
        return view_holder;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView img, comment;
        TextView title, description, timestamp, viewComment;
        ProgressBar progressBar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.ratingsprogressBar);
            img = (ImageView) itemView.findViewById(R.id.img1);
            comment = (ImageView) itemView.findViewById(R.id.speech_bubble);
            viewComment = (TextView) itemView.findViewById(R.id.image_comments_link);

            title = (TextView) itemView.findViewById(R.id.titletext);
            description = (TextView) itemView.findViewById(R.id.descriptiontext);
            timestamp = (TextView) itemView.findViewById(R.id.image_time_posted);

        }
    }

//    private void getComments(String postid, TextView viewComments) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ratings").child(postid);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                long count = snapshot.getChildrenCount();
//                if (count == 0) {
//                    viewComments.setText("");
//                } else if (count == 1) {
//                    viewComments.setText("View All " + snapshot.getChildrenCount() + " Comment");
//                } else {
//                    viewComments.setText("View All " + snapshot.getChildrenCount() + " Comments");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


}
