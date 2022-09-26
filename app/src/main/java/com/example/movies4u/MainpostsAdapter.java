package com.example.movies4u;

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

import java.util.concurrent.RecursiveAction;

public class MainpostsAdapter extends FirebaseRecyclerAdapter<MainpostsModel,MainpostsAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainpostsAdapter(@NonNull FirebaseRecyclerOptions<MainpostsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainpostsModel model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(R.drawable.movie_icon)
                .error(R.drawable.cannot_load_item)
                .into(holder.img);
        Log.d("post1", model.getTitle());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_posts_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title,description;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img1);
            title=(TextView) itemView.findViewById(R.id.titletext);
            description=(TextView) itemView.findViewById(R.id.descriptiontext);
        }
    }
}
