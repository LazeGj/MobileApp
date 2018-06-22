package com.example.android.explicitintent;

/**
 * Created by Laze on 6/22/2018.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laze on 6/21/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    public static List<Movie> movieList;
    private final MoviesAdapterOnClickHandler clickHandler;

    public MovieAdapter(MoviesAdapterOnClickHandler b) {
    clickHandler=b;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.list_item,parent,false);

        return new MovieViewHolder(view);

    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(int MoviewId);
    }
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie m=movieList.get(position);
        holder.ReleseDate.setText(m.getReleaseDate());
        holder.textTitle.setText(m.getName());
        holder.textRating.setText(m.getRating());
       // holder.ImageView.setImageDrawable(m.image.getDrawable());
    }

    @Override
    public int getItemCount() {
        if(movieList!=null)
            return movieList.size();
        return 0;
    }

    public void setMovies(List<Movie> movies)
    {
       movieList=movies;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView textTitle;
        public final TextView textRating;
        public final TextView ReleseDate;
        //public final ImageView ImageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            textRating=(TextView)itemView.findViewById(R.id.tv_rating);
            textTitle=(TextView)itemView.findViewById(R.id.text_view_title);
            ReleseDate=(TextView)itemView.findViewById(R.id.relese_date);
            //ImageView=(ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int adapterPo=getAdapterPosition();

            clickHandler.onClick(adapterPo);
        }
    }



}
