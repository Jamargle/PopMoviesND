package com.udacity.popmoviesnd.domain.repository;

import com.udacity.popmoviesnd.domain.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface for accessing to Movie information through network resources
 */
public interface NetworkMovieGateway {

    /**
     * Retrieve the list of Movies
     *
     * @return Observable with the List of Movies
     */
    Observable<List<Movie>> obtainMovies();

}
