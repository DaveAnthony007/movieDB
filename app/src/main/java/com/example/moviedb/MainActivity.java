package com.example.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public static List<Movie> listMovie;
    private ApiHandler apiHandler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rcv_movie_list);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        listMovie = new ArrayList<>();
        apiHandler = new ApiHandler(this);

        // dummy
        for (int i = 0; i < 20; i++) {
            Movie newMovie = new Movie();
            newMovie.setTitle("Movie " + i);
            listMovie.add(newMovie);
        }

//        apiHandler.getPopularMovie();
//        Log.d(TAG, "onCreate: " + apiHandler.temp);
//        for (Movie m :
//                apiHandler.temp) {
//            Log.d(TAG, "onCreate: " + m.getTitle());
//        }
//        listMovie = apiHandler.temp;

//        Log.d(TAG, "onCreate: " + apiHandler.res);

        something();
//        adapter.notifyDataSetChanged();
        Log.d(TAG, "onCreate: " + listMovie);

//        adapter = new MovieAdapter(this.listMovie, this);
//        recyclerView.setAdapter(adapter);




    }

    private void something() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=6ad6a1d90315687aae2dd166fb5491b2&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: Requesting");

                        adapter = new MovieAdapter(jsonParseToMovie(response), getApplicationContext());

                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Request movie error");
                    }
                });
        requestQueue.add(stringRequest);
    }
    public List<Movie> jsonParseToMovie(String response) {
        List<Movie> listMovie = new ArrayList<>();
//        res = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
//            JSONObject movieObj = jsonArray.getJSONObject(0);
            JSONObject movieObj;
            Movie newMovie;
            Log.d(TAG, "jsonParseToMovie: JSON ARRAY SIZE " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i ++) {
                newMovie = new Movie();
                movieObj = jsonArray.getJSONObject(i);

                newMovie.setTitle(movieObj.getString("original_title"));
                newMovie.setImage_url(movieObj.getString("poster_path"));
                newMovie.setYear(movieObj.getString("release_date").toString().split("-")[0]);
                newMovie.setDesc(movieObj.getString("overview"));

                listMovie.add(newMovie);
            }
        } catch (Exception e) {
            Log.d(TAG, "jsonParseToMovie: Parsing error");
        }

        Helper.listMovie = listMovie;
        for (Movie m :
                listMovie) {
            Log.d(TAG, "jsonParseToMovie: " + m.getTitle());
        }
        return listMovie;
    }
}