package com.moviesearch.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Movie {
    @SerializedName(value = "title", alternate = {"Title"})
    private String title;
    @SerializedName(value = "year", alternate = {"Year"})
    private String year;
    private String imdbID;
    @SerializedName(value = "type", alternate = {"Type"})
    private String type;
    @SerializedName(value = "poster", alternate = {"Poster"})
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year.toString();
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrettyIMDB() {
        return "IMDB: " + imdbID.toUpperCase(Locale.ROOT);
    }

    public String getImdb(){return imdbID;}

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return " " + type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
