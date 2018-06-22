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
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.android.explicitintent.NetworkHandling.JsonMovie;
import com.example.android.explicitintent.NetworkHandling.NetworkHandling;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Button popular;
    private Button topRated;

    private Button lattest;
    private Button upcoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popular = (Button) findViewById(R.id.popular);
        topRated=(Button)findViewById(R.id.top_rated);
        lattest=(Button)findViewById(R.id.lattest);
        upcoming=(Button)findViewById(R.id.upcoming);


        popular.setOnClickListener(new OnClickListener() {

            /**
             * The onClick method is triggered when this button (mDoSomethingCoolButton) is clicked.
             *
             * @param v The view that is clicked. In this case, it's mDoSomethingCoolButton.
             */
            @Override
            public void onClick(View v) {
                String textEntered = "popular";
               createActivity(textEntered);
            }
        });

        topRated.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String par="top_rated";
                createActivity(par);
            }
        });
        lattest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String par="latest";
                createActivity(par);
            }
        });
        upcoming.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String par="upcoming";
                createActivity(par);
            }
        });

    }

    void createActivity(String parametar)
    {
        String textEntered = parametar;
        Context context = MainActivity.this;
        Class destinationActivity = ChildActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);
        startActivity(startChildActivityIntent);
    }
}

