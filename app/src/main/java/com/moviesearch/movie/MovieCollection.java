package com.moviesearch.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCollection {
    @SerializedName(value = "movies", alternate = {"Search"})
    ArrayList<Movie> movies = new ArrayList<>();
    @SerializedName(value = "totalResults", alternate = {"TotalResults"})
    Long totalResults;
    @SerializedName(value = "response", alternate = {"Response"})
    boolean response;
    @SerializedName(value = "error", alternate = {"Error"})
    String error;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public boolean hasResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}