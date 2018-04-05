package com.udacity.popmoviesnd.domain.repository;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.model.MovieReview;
import com.udacity.popmoviesnd.domain.model.Sorting;
import com.udacity.popmoviesnd.domain.model.Video;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface for accessing to Movie information through network resources
 */
public interface NetworkMovieGateway {

    /**
     * Retrieve the list of Movies matching the given criteria
     *
     * @param criteria Sorting criteria to retrieve movies. It can be all, only most popular or only top rated
     * @return Observable with the List of Movies
     */
    Observable<List<Movie>> obtainMovies(@Sorting int criteria);

    Observable<List<Video>> obtainTrailers(int movieId);

    Observable<List<MovieReview>> obtainReviews(int movieId);

}
