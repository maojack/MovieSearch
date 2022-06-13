package com.moviesearch.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.moviesearch.R;
import com.moviesearch.movie.Movie;
import com.moviesearch.movie.MovieCollection;
import com.moviesearch.movie.SearchResult;
import com.moviesearch.network.OmdbApi;
import com.moviesearch.ui.adapter.SearchResultAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String KEY = "863c2929";
    RecyclerView recyclerView;
    SearchView searchView;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        setupRecyclerView();
        setupSearchView();
    }

    private void setupRetrofit(String query){
        OmdbApi api = retrofit.create(OmdbApi.class);
        Call<MovieCollection> call = api.getSearchResult(query, KEY, 1);
        call.enqueue(new Callback<MovieCollection>() {
            @Override
            public void onResponse(@NonNull Call<MovieCollection> call, @NonNull Response<MovieCollection> response) {
                if (response.code() != 200){
                    Log.e("", "Something is wrong with network");
                    return;
                }
                MovieCollection movieCollection = response.body();
                assert movieCollection != null;
                for (Movie m: movieCollection.getMovies()){
                    Log.e("", m.getTitle());
                }
                SearchResult.getInstance().setResults(movieCollection.getMovies());
                TextView textView = findViewById(R.id.text_blank_list);
                if (movieCollection.hasResponse()){
                    setupRecyclerView();
                    textView.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(movieCollection.getError());
                }
            }

            @Override
            public void onFailure(Call<MovieCollection> call, Throwable t) {

            }
        });
    }

    private void setupSearchView(){
        searchView = findViewById(R.id.search_movie);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // call REST API
                setupRetrofit(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // do nothing
                return false;
            }
        });
    }

    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.recycler_movie_result);
        SearchResultAdapter adapter = new SearchResultAdapter(
                        getApplicationContext(),
                        SearchResult.getInstance().getResults()
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)){
                    addMorePage();
                }
            }
        });
    }

    private void addMorePage(){
        SearchResult.getInstance().incrementPageNum();
        OmdbApi api = retrofit.create(OmdbApi.class);
        Call<MovieCollection> call = api.getSearchResult(searchView.getQuery().toString(), KEY, SearchResult.getInstance().getPageNum());
        call.enqueue(new Callback<MovieCollection>() {
            @Override
            public void onResponse(@NonNull Call<MovieCollection> call, @NonNull Response<MovieCollection> response) {
                if (response.code() != 200){
                    Log.e("", "Something is wrong with network");
                    return;
                }
                MovieCollection movieCollection = response.body();
                assert movieCollection != null;
                for (Movie m: movieCollection.getMovies()){
                    Log.e("", m.getTitle());
                }
                SearchResult.getInstance().appendMovies(movieCollection.getMovies());
                if (movieCollection.hasResponse()){
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieCollection> call, Throwable t) {

            }
        });
    }
}