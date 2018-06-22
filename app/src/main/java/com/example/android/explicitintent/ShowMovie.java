package com.example.android.explicitintent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.explicitintent.NetworkHandling.NetworkHandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowMovie extends AppCompatActivity {

    String idForYoutube;
    Movie movie;
    TextView tittle;
    TextView desc;
    TextView rating;
    TextView releseDay;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        String movieDetails=null;
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movieDetails= intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

        }
        int pos=Integer.parseInt(movieDetails);
        movie=MovieAdapter.movieList.get(pos);
        btn=(Button)findViewById(R.id.watch_trailer);
        tittle=(TextView)findViewById(R.id.title);
        desc=findViewById(R.id.overview);
        releseDay=findViewById(R.id.relese_date);
        rating=findViewById(R.id.rating);

        new GetYouTubeId().execute(movie.getId());




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = ShowMovie.this;
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + idForYoutube));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + idForYoutube));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {

                    startActivity(appIntent);
                }
            }
            }
        );



    }

    @Override
    protected void onStart() {
        super.onStart();
        tittle.setText(movie.getName());
        desc.setText("Short Description \n"+movie.getDescription());
        releseDay.setText("Relese date:"+movie.getReleaseDate());
        rating.setText("Rating :"+movie.getRating());
    }

    class GetYouTubeId extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            String movieId=strings[0];

            URL url=null;
            try {
                url=NetworkHandling.builtURLForYouTube(movieId);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String responseJson=null;
            try {
                responseJson=NetworkHandling.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray=null;
            try {
                JSONObject jsonObject=new JSONObject(responseJson);
                jsonArray=jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String toBeReturned=null;
            try {
                toBeReturned= jsonArray.getJSONObject(0).getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return toBeReturned;
        }

        @Override
        protected void onPostExecute(String s) {
            idForYoutube=s;
            super.onPostExecute(s);
        }
    }

}
