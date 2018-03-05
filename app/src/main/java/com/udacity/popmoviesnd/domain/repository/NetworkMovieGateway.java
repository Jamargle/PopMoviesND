package com.udacity.popmoviesnd.domain.repository;

import com.udacity.popmoviesnd.domain.model.Movie;

import java.util.List;

/**
 * Interface for accessing to Movie information through network resources
 */
public interface NetworkMovieGateway {

    /**
     * Retrieve the list of Movies
     *
     * @return List of Movies
     */
    List<Movie> obtainMovies();

}
