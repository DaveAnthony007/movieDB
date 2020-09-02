package com.example.moviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = "DetailActivity";

    private TextView txtTitle, txtDesc, txtYear, txtGenre;
    private ImageView imgView;
    private Movie currMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtYear = (TextView) findViewById(R.id.txt_desc);
        txtGenre = (TextView) findViewById(R.id.txt_genre);
        txtDesc = (TextView) findViewById(R.id.txt_desc);
        imgView = (ImageView) findViewById(R.id.img_movie);

        currMovie = (Movie) getIntent().getSerializableExtra("MovieObj");

        txtTitle.setText(currMovie.getTitle());
        txtYear.setText(currMovie.getYear());
        txtDesc.setText(currMovie.getDesc());
        String image_url = ApiHandler.requestImage(currMovie.getImage_url());
        Picasso.get().load(image_url).into(imgView);
    }
}