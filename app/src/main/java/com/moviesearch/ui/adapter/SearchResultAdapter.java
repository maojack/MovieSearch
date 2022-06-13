package com.moviesearch.ui.adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moviesearch.R;
import com.moviesearch.movie.Movie;
import com.moviesearch.movie.MovieDetail;
import com.moviesearch.network.OmdbApi;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    public SearchResultAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        String yearType = movie.getYear() + movie.getType();
        holder.year.setText(yearType);
        holder.imdb.setText(movie.getPrettyIMDB());
        holder.detailView.setVisibility(View.GONE);
        Picasso.get()
                .load(movie.getPoster())
                .error(R.drawable.img_default_poster)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView title;
        private TextView year;
        private TextView imdb;
        private RelativeLayout detailView;
        private TextView plot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_movie_poster);
            title = itemView.findViewById(R.id.text_movie_title);
            year = itemView.findViewById(R.id.text_movie_year);
            imdb = itemView.findViewById(R.id.text_imdb);
            detailView = itemView.findViewById(R.id.view_movie_detail);
            plot = itemView.findViewById(R.id.text_movie_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (detailView.getVisibility() == View.VISIBLE){
                        TransitionManager.beginDelayedTransition((ViewGroup) itemView,new AutoTransition());
                        detailView.setVisibility(View.GONE);
                    }else{
                        setupRetrofit((String) title.getText());
                        TransitionManager.beginDelayedTransition((ViewGroup) itemView,new AutoTransition());
                        detailView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        private void setupRetrofit(String imdbID){
            final String KEY = "863c2929";
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OmdbApi api = retrofit.create(OmdbApi.class);
            Call<MovieDetail> call = api.getMovieDetail(imdbID, KEY);
            call.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                    if (response.code() != 200){
                        Log.e("", "Something is wrong with network");
                        return;
                    }
                    MovieDetail movieDetail = response.body();
                    TextView textView = detailView.findViewById(R.id.text_movie_detail);
                    assert movieDetail != null;
                    textView.append(movieDetail.getPlot() + "");
                    Log.e("", "request: " + call.request().url());
                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {

                }
            });
        }
    }


}
