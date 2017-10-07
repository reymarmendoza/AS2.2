package com.example.reyma.moviesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reyma.moviesapp.Activities.MovieDetailActivity;
import com.example.reyma.moviesapp.Models.Movie;
import com.example.reyma.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by reyma on 17/03/2017.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    List<Movie> movies;
    Context context;

    public MovieRecyclerViewAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    // se crean los nuevos views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);

    }

    private Context getContext(){
        return context;
    }
    // reemplaza los contenidos de los views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        // movie es la clase que contiene los parametros de mi objeto
        Picasso.with(getContext()).load(movie.getPosterPath()).into(holder.ivMovieImage);

    }
    // retorna el tamaña del dataset
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvOverview)
        TextView tvOverview;
        @BindView(R.id.cvMovie)
        CardView cvMovie;

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }
        // cuando haga click sobre un elemento cardview que se muestra en el adapter inicia el intent para arrancar la class
        @Override
        public void onClick(View v) {
            // obtener la info de la movie seleccionada
            Movie movie = movies.get(getAdapterPosition());
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            // envio info en el intent paa obtener la informacion
            intent.putExtra("MOVIE", movie);
            // se añade implements Serializable a Movie.java para poder enviar info entre activities
            getContext().startActivity(intent);
        }
    }
}
