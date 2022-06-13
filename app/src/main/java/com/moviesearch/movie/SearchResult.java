package com.moviesearch.movie;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResult {
    private ArrayList<Movie> results = new ArrayList<>();
    private int page = 1;
    private static SearchResult instance = null;
    private SearchResult(){}
    public static SearchResult getInstance(){
        if (instance == null){
            instance = new SearchResult();
        }
        return instance;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public int getResultSize(){
        return this.results.size();
    }

    public Movie get(int i){
        return results.get(i);
    }

    public void appendMovies(ArrayList<Movie> movies) {
        this.results.addAll(movies);
    }

    public void incrementPageNum(){
        page++;
    }

    public int getPageNum(){
        return page;
    }

    public void resetSearch(){
        results.clear();
        page = 0;
    }

}