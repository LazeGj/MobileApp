package com.example.android.explicitintent.NetworkHandling;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.android.explicitintent.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Laze on 6/22/2018.
 */

public class MovieParser {



    private static String ID="id";
    private static String TITLE="title";
    private static String RATING="vote_average";
    private static String DESCRIPTION="overview";
    private static String RELEASE_DATE="release_date";
    private static String PATH="poster_path";
    public static Movie getFromJson(JSONObject jsonObject) throws JSONException {
            String id=jsonObject.getString(ID);
            String title=jsonObject.getString(TITLE);
            String rating=jsonObject.getString(RATING);
            String descriptin=jsonObject.getString(DESCRIPTION);
            String releaseDate=jsonObject.getString(RELEASE_DATE);
            String path=jsonObject.getString(PATH);

            return new Movie(id,title,rating,descriptin,releaseDate,path);
    }
}
