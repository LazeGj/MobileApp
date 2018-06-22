package com.example.android.explicitintent;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Laze on 6/22/2018.
 */

public class Movie {

    class Download extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... strings) {
            //imageLoader.handleSlowNetwork(true);
            imageLoader.displayImage(strings[0],image);

            return null;
        }
    }

    private static ImageLoader imageLoader=ImageLoader.getInstance();
    private  String BASE_URL_FOR_PICTURE="http://image.tmdb.org/t/p/w92";

    private String Id;
    private String name;
    private String Rating;
    private String Description;
    private String ReleaseDate;
    private String ImageUrl;
    public ImageView image;

    public Movie(String id,String name, String rating, String description, String releaseDate,String path) {
        Id=id;
        this.name = name;
        Rating = rating;
        ImageUrl=BASE_URL_FOR_PICTURE+path;
        //imageLoader.displayImage(ImageUrl,image);
        Description = description;
        ReleaseDate = releaseDate;

        //image=new ImageView();

    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getId() {
        return Id;
    }

    /*public void setImage(ImageView image) {
        this.image = image;
    }*/

    public String getName() {
        return name;
    }

    public String getRating() {
        return Rating;
    }

    public String getDescription() {
        return Description;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    @Override
    public String toString() {
        return name+Rating+Description+ReleaseDate;
    }
}
