package com.example.moviedb;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ApiHandler {
    private final String TAG = "ApiHandler";
    private final String API_KEY = "6ad6a1d90315687aae2dd166fb5491b2";
    private RequestQueue requestQueue;
    private Context context;

    public ApiHandler(Context _context) {
        context = _context;
        requestQueue = Volley.newRequestQueue(_context);
        Log.d(TAG, "ApiHandler: constructor");
    }

    public static String requestImage(String image_url) {
        String url = "https://image.tmdb.org/t/p/w500/" + image_url;
        return url;
    }

    public void getPopularMovie(final RecyclerView.Adapter adapter) {
        Log.d(TAG, "getPopularMovie: Fetch data");
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=6ad6a1d90315687aae2dd166fb5491b2&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: Requesting");
                        ((MovieAdapter) adapter).setListMovie(jsonParseToMovie(response));
                        adapter.notifyDataSetChanged();
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

        return listMovie;
    }

}
