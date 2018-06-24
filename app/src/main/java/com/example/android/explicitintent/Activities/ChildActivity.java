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
package com.example.android.explicitintent.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.explicitintent.Movie;
import com.example.android.explicitintent.MovieAdapter;
import com.example.android.explicitintent.NetworkHandling.JsonMovie;
import com.example.android.explicitintent.NetworkHandling.NetworkHandling;
import com.example.android.explicitintent.R;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ChildActivity extends AppCompatActivity implements MovieAdapter.MoviesAdapterOnClickHandler {


    private static String typeSearched;
    private static String BUNDLE_KEY_MOVIES="MOVIES";
    private ArrayList<Movie> movieList;
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

       /* if(savedInstanceState!=null && movieList==null)
            loadDataFromBundle(savedInstanceState);*/


    }

    /*boolean loadDataFromBundle(Bundle savedInstance)
    {
        if(savedInstance.containsKey(BUNDLE_KEY_MOVIES))
        {
            movieList= (ArrayList<Movie>) savedInstance.get(BUNDLE_KEY_MOVIES);
            movieAdapter.setMovies(movieList);
            return true;
        }
        return false;
    }*/
    void loadData()
    {
        new FetchDataFromApi().execute(typeSearched);
    }

    void loadImages(ArrayList<Movie> movies)
    {

        new Download().execute(movies);
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





    class FetchDataFromApi extends AsyncTask<String,Void,ArrayList<Movie>>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
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

            ArrayList<Movie> list=null;
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
        protected void onPostExecute(ArrayList<Movie> movies) {
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
            movieList=movies;
            movieAdapter.setMovies(movieList);
            if(movieList!=null)
                loadImages(movieList);
        }
        }

   /* @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        ArrayList<Movie> movieArrayList=movieList;
        outState.putSerializable(BUNDLE_KEY_MOVIES,movieArrayList);
    }*/


    class Download extends AsyncTask<ArrayList<Movie>,Void,Void>
    {

        @Override
        protected Void doInBackground(ArrayList<Movie>[] arrayLists) {
            ArrayList<Movie> movieArrayList=arrayLists[0];
            //ArrayList<BitmapDrawable> moviesImages;
            for(int i=0;i<movieArrayList.size();++i) {
                Movie currentMovie=movieArrayList.get(i);
                URL url = null;
                try {
                    url = new URL(currentMovie.getImageUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap toBeReturn = null;
                InputStream in = null;
                try {
                    in = url.openStream();
                    toBeReturn = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable d=new BitmapDrawable(getResources(),toBeReturn);
                currentMovie.image=d;

            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            movieAdapter.notifyDataChange();
            super.onPostExecute(aVoid);
        }
    }
}
