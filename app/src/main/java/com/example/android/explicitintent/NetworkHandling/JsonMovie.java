package com.example.android.explicitintent.NetworkHandling;

import com.example.android.explicitintent.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laze on 6/22/2018.
 */

public class JsonMovie {


    private static String RESULTS="results";
public static ArrayList<Movie> getListMoviesFromJson(String moviesJsonRawFormat) throws JSONException {

    ArrayList<Movie> movies=new ArrayList<>();

    JSONObject jsonObject=new JSONObject(moviesJsonRawFormat);

    JSONArray moviesArray=jsonObject.getJSONArray(RESULTS);
    int lenght=moviesArray.length();
    for(int i=0;i<lenght;++i)
    {

        //geting json from a single movie
        JSONObject singleMovie=moviesArray.getJSONObject(i);
        //creating a list of movies
        movies.add(MovieParser.getFromJson(singleMovie));
    }
    return movies;
}

}
