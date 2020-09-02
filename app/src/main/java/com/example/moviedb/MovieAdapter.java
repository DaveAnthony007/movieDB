package com.example.moviedb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final String TAG = "MovieAdapter";
    private List<Movie> listMovie;
    private Context context;



    public MovieAdapter(List _listMovie, Context _context) {
        listMovie = _listMovie;
        context = _context;
        Log.d(TAG, "MovieAdapter: " + _listMovie);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Movie currMovie = listMovie.get(position);
        holder.txtTitle.setText(currMovie.getTitle());
//        holder.txtDesc.setText(currMovie.getDesc());
        String image_url = ApiHandler.requestImage(currMovie.getImage_url());
        Picasso.get().load(image_url).into(holder.imgMovie);
//        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.imgMovie);

        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("MovieObj", listMovie.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtDesc;
        public ImageView imgMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgMovie = (ImageView) itemView.findViewById(R.id.img_movie);
//            txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
        }
    }
}
