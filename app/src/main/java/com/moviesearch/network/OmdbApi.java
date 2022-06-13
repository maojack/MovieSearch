package com.moviesearch.network;
import com.moviesearch.movie.MovieCollection;
import com.moviesearch.movie.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {
    @GET("/")
    Call<MovieCollection> getSearchResult(@Query("s") String s, @Query("apikey") String key, @Query("page") Integer i);

    @GET("/")
    Call<MovieDetail> getMovieDetail(@Query("t") String id, @Query("apikey") String key);
}