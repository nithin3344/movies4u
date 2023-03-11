package com.example.movies4u;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.concurrent.RecursiveAction;

public class newvideosAdapter extends FirebaseRecyclerAdapter<new_videos_model,newvideosAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public newvideosAdapter(@NonNull FirebaseRecyclerOptions<new_videos_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull new_videos_model model) {
        holder.title.setText(model.getTitle());
//        model.getImage_url()
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url= model.getLink();
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//
//                startActivity(view.getContext(), intent);
//
//            }
//        });

        Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(R.drawable.movie_icon)
                .error(R.drawable.cannot_load_item)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newvideos_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img1);
            title=(TextView) itemView.findViewById(R.id.titletext);
        }
    }
}
