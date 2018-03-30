package com.udacity.popmoviesnd.domain.repository;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.Sorting;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface for accessing to Movie information in local sources
 */
public interface LocalMovieGateway {

    /**
     * Retrieve the list of Movies matching the given criteria
     *
     * @param criteria Sorting criteria to retrieve movies. It can be all, only most popular or only top rated
     * @return Observable with the List of Movies
     */
    Observable<List<Movie>> obtainMovies(@Sorting int criteria);

    void persist(List<Movie> moviesToPersist);

    /**
     * Updates a movie in the local database
     *
     * @param movieToUpdate Movie to be updated
     * @return Number of rows updated
     */
    int update(Movie movieToUpdate);

    void delete(Movie movieToDelete);

}
