/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.explicitintent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.explicitintent.NetworkHandling.JsonMovie;
import com.example.android.explicitintent.NetworkHandling.NetworkHandling;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ChildActivity extends AppCompatActivity implements MovieAdapter.MoviesAdapterOnClickHandler {


    private static String typeSearched;

    private List<Movie> movieList;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    TextView textViewError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        progressBar=(ProgressBar)findViewById(R.id.progress_barr);

        recyclerView=(RecyclerView)findViewById(R.id.rv_movies);

        textViewError=findViewById(R.id.errorMessage);

        movieAdapter=new MovieAdapter(this);

        recyclerView.setAdapter(movieAdapter);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
             typeSearched= intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

        }

    loadData();
    }

    void loadData()
    {
        new FetchDataFromApi().execute(typeSearched);
    }

    @Override
    public void onClick(int MoviewId) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(MoviewId);


        Context context = ChildActivity.this;
        Class destinationActivity = ShowMovie.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,stringBuilder.toString() );
        startActivity(startChildActivityIntent);
        //what to happen if item is clicked
    }





    class FetchDataFromApi extends AsyncTask<String,Void,List<Movie>>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String type=strings[0];
            URL url=null;
            try {
                url= NetworkHandling.builtURL(type);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String JSONrespnse = null;
            try {
                JSONrespnse= NetworkHandling.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Movie> list=null;
            try {
                list= JsonMovie.getListMoviesFromJson(JSONrespnse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                return list;
            }
        }

        //To Do send the list to the adapter


        @Override
        protected void onPostExecute(List<Movie> movies) {
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(movies);
            if(movies==null)
            {
                recyclerView.setVisibility(View.INVISIBLE);
                textViewError.setVisibility(View.VISIBLE);

            }
            else
            {
                recyclerView.setVisibility(View.VISIBLE);
                textViewError.setVisibility(View.INVISIBLE);
            }
            movieAdapter.setMovies(movies);
        }






        }
    }

   /* class DonwnloadImagge extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String path=strings[0];
            URL url= null;
            try {
                url = new URL(path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap toBeReturn=null;
            try {
                toBeReturn= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return toBeReturn;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image=null;
            image=bitmap;
            super.onPostExecute(bitmap);
        }
    }*/
