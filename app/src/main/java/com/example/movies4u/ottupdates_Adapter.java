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

public class ottupdates_Adapter extends FirebaseRecyclerAdapter<ottupdates_Model,ottupdates_Adapter.myViewHolder> {
    private static final String TAG = "ottupdates_Adapter";

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ottupdates_Adapter(@NonNull FirebaseRecyclerOptions<ottupdates_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ottupdates_Model model) {
        holder.title.setText(model.getTitle());
        Log.d(TAG, model.getTitle());
        holder.description.setText(model.getDescription());
        holder.date.setText(model.getDate());
        holder.platform.setText(model.getPlatform());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(R.drawable.movie_icon)
                .error(R.drawable.cannot_load_item)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ottupdates_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title,description,date,platform;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img1);
            title=(TextView) itemView.findViewById(R.id.titletext);
            description=(TextView) itemView.findViewById(R.id.descriptiontext);
            date=(TextView) itemView.findViewById(R.id.datetext);
            platform=(TextView) itemView.findViewById(R.id.platformtext);
        }
    }
}
