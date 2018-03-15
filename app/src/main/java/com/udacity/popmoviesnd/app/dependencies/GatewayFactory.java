package com.udacity.popmoviesnd.app.dependencies;

import com.udacity.popmoviesnd.app.util.ServiceGenerator;
import com.udacity.popmoviesnd.data.network.MovieDbClient;
import com.udacity.popmoviesnd.data.network.NetworkMovieGatewayImp;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

/**
 * Helper class to perform Gateway creations
 */
abstract class GatewayFactory {

    private static NetworkMovieGateway networkMovieGatewayInstance;

    private GatewayFactory() {

    }

    static NetworkMovieGateway makeNetworkMovieGateway() {
        if (networkMovieGatewayInstance == null) {
            networkMovieGatewayInstance = new NetworkMovieGatewayImp(ServiceGenerator.createService(MovieDbClient.class));
        }
        return networkMovieGatewayInstance;
    }

}
