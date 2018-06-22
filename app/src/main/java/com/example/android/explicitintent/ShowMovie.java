package com.example.android.explicitintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ShowMovie extends AppCompatActivity {

    Movie movie;
    TextView tittle;
    TextView desc;
    TextView rating;
    TextView releseDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        tittle=(TextView)findViewById(R.id.title);
        desc=findViewById(R.id.overview);
        releseDay=findViewById(R.id.relese_date);
        rating=findViewById(R.id.rating);


        String movieDetails=null;
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movieDetails= intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

        }
       int pos=Integer.parseInt(movieDetails);
        movie=MovieAdapter.movieList.get(pos);

      tittle.setText(movie.getName());
      desc.setText("Short Description \n"+movie.getDescription());
      releseDay.setText("Relese date:"+movie.getReleaseDate());
      rating.setText("Rating :"+movie.getRating());
    }

}
