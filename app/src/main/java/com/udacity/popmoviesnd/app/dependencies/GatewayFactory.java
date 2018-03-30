package com.udacity.popmoviesnd.app.dependencies;

import android.content.Context;

import com.udacity.popmoviesnd.app.util.ServiceGenerator;
import com.udacity.popmoviesnd.data.local.LocalMovieGatewayImp;
import com.udacity.popmoviesnd.data.network.MovieDbClient;
import com.udacity.popmoviesnd.data.network.NetworkMovieGatewayImp;
import com.udacity.popmoviesnd.domain.repository.LocalMovieGateway;
import com.udacity.popmoviesnd.domain.repository.NetworkMovieGateway;

/**
 * Helper class to perform Gateway creations
 */
abstract class GatewayFactory {

    private static NetworkMovieGateway networkMovieGatewayInstance;
    private static LocalMovieGateway localMovieGatewayInstance;

    private GatewayFactory() {

    }

    static NetworkMovieGateway makeNetworkMovieGateway() {
        if (networkMovieGatewayInstance == null) {
            networkMovieGatewayInstance = new NetworkMovieGatewayImp(ServiceGenerator.createService(MovieDbClient.class));
        }
        return networkMovieGatewayInstance;
    }

    public static LocalMovieGateway makeLocalMovieGateway(final Context context) {
        if (localMovieGatewayInstance == null) {
            localMovieGatewayInstance = new LocalMovieGatewayImp(context);
        }
        return localMovieGatewayInstance;
    }

}
