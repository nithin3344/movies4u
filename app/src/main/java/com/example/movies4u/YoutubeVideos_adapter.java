package com.example.movies4u;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class YoutubeVideos_adapter extends FirebaseRecyclerAdapter<YoutubeVideos_Model,YoutubeVideos_adapter.myViewHolder> {
    private static final String TAG = "YoutubeVideos_adapter";

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public YoutubeVideos_adapter(@NonNull FirebaseRecyclerOptions<YoutubeVideos_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull YoutubeVideos_Model model) {
        holder.title.setText(model.getTitle());
        Log.d(TAG, model.getTitle());
        holder.link.setText(model.getLink());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse(model.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
            }
        });

        Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(R.drawable.movie_icon)
                .error(R.drawable.cannot_load_item)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtubevideos_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title,link;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img1);
            title=(TextView) itemView.findViewById(R.id.titletext);
            link=(TextView) itemView.findViewById(R.id.linktext);
        }
    }
}
