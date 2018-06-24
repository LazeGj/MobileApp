package com.example.android.explicitintent.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.explicitintent.Movie;
import com.example.android.explicitintent.MovieAdapter;
import com.example.android.explicitintent.NetworkHandling.NetworkHandling;
import com.example.android.explicitintent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowMovie extends AppCompatActivity {

    String idForYoutube;
    Movie movie;
    TextView tittle;
    TextView desc;
    TextView rating;
    TextView releseDay;
    ImageView img=null;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        String movieDetails=null;
        Intent intentThatStartedThisActivity = getIntent();
        img=(ImageView)findViewById(R.id.image_movie);

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movieDetails= intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

        }
        int pos=Integer.parseInt(movieDetails);
        movie= MovieAdapter.movieList.get(pos);
        btn=(Button)findViewById(R.id.watch_trailer);
        String ImageUrl=movie.getImageUrl();
        tittle=(TextView)findViewById(R.id.title);
        desc=findViewById(R.id.overview);
        releseDay=findViewById(R.id.relese_date);
        rating=findViewById(R.id.rating);

        new GetYouTubeId().execute(movie.getId());
        new ImageDownlaod().execute(ImageUrl);




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
        String overView="Story:\n"+movie.getDescription();
        String releseDate="Release date:\n"+movie.getReleaseDate();
        String rating="Rating: "+movie.getRating();
        tittle.setText(movie.getName());
        desc.setText(overView);
        releseDay.setText(releseDate);
        this.rating.setText(rating);
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

    class ImageDownlaod extends AsyncTask<String,Void,Drawable>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... strings) {

            String UrlString=strings[0];
            URL url=null;
            try {
                url=new URL(UrlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap toBeReturned=null;
            InputStream in = null;
            try {
                in = url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            toBeReturned = BitmapFactory.decodeStream(in);

            Drawable drawable=new BitmapDrawable(getResources(),toBeReturned);
            return drawable;

        }

        @Override
        protected void onPostExecute(Drawable d) {

            img.setImageDrawable(d);
            super.onPostExecute(d);
        }
    }

}
