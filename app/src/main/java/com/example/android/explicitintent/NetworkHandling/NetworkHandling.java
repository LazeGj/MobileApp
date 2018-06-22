package com.example.android.explicitintent.NetworkHandling;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Laze on 6/22/2018.
 */

public class NetworkHandling {


    private static final String ApiKey="d2d71b557a770efb4b9b2bd22c69c877";

    private static final String BASE_URL="https://api.themoviedb.org/3";

    private static final String API_PARAM="api_key";
    private static final String MOVIE_PARAM="movie";

    //movieFilter examples: latest, popular...
    public static URL builtURL(String MovieFilter) throws MalformedURLException {
        if(MovieFilter==null)
            MovieFilter="popular";
        Uri uriBuilder=Uri.parse(BASE_URL).buildUpon()
                .appendPath(MOVIE_PARAM)
                .appendPath(MovieFilter)
                .appendQueryParameter(API_PARAM,ApiKey)
                .build();

        return new URL(uriBuilder.toString());
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
