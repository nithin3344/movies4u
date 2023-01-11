package com.example.movies4u;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class comments_Adapter extends FirebaseRecyclerAdapter<comment_model,comments_Adapter.myViewHolder> {
    private static final String TAG = "Comment_Adapter";

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param view_comments_activity
     */
    public comments_Adapter(@NonNull FirebaseRecyclerOptions<comment_model> options, view_Comments_Activity view_comments_activity) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull comments_Adapter.myViewHolder holder, int position, @NonNull comment_model model) {
        final String commentKey=getRef(position).getKey();
        final String postKey=getRef(position).getParent().getParent().getKey();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId=user.getUid();
        holder.username.setText(model.getUsername());
        holder.comment.setText(model.getUsermsg());
        holder.time.setText("Date= "+model.getDate()+"Time= "+model.getTime());
        if(!model.getUid().equals(userId))
            holder.delete_icon.setVisibility(View.GONE);

        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete the comment");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete comment
                        deleteComment(commentKey, postKey, view);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                //show dialog
                builder.create().show();
            }
        });

    }

    private void deleteComment(String commentKey,String postKey,View view) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().
                child("ratings").child(postKey).child("comments");
        final DatabaseReference post_ref = FirebaseDatabase.getInstance().getReference().
                child("ratings").child(postKey);
        ref.child(commentKey).removeValue().addOnSuccessListener(unused -> Toast.makeText(view.getContext(), "Comment Deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(view.getContext(), "Unable to delete Comment", Toast.LENGTH_SHORT).show());
    }



    @NonNull
    @Override
    public comments_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_item,parent,false);
        return new comments_Adapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView username,comment,time;
        ImageView delete_icon;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            username=(TextView) itemView.findViewById(R.id.comment_username);
            comment=(TextView) itemView.findViewById(R.id.comment);
            time =(TextView) itemView.findViewById(R.id.comment_time_posted);
            delete_icon=(ImageView) itemView.findViewById(R.id.comment_delete);
        }
    }
}
