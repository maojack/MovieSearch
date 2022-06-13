package com.moviesearch.movie;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName(value = "source", alternate = {"Source"})
    private String source;
    @SerializedName(value = "value", alternate = {"Value"})
    private String value;
}
