package com.udacity.popmoviesnd.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class MoviePage {

    @SerializedName("page") private int numPage;
    @SerializedName("results") private List<Movie> movies;

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(final int numPage) {
        this.numPage = numPage;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

}
