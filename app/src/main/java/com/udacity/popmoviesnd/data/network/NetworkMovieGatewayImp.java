package com.udacity.popmoviesnd.data.network;

import com.udacity.popmoviesnd.domain.model.Movie;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

import java.util.ArrayList;
import java.util.List;

public final class NetworkMovieGatewayImp implements NetworkMovieGateway {

    @Override
    public List<Movie> obtainMovies() {
        return new ArrayList<>();
    }

}
