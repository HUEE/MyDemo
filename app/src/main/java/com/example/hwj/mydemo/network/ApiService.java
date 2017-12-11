package com.example.hwj.mydemo.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ivan on 8/20/2017.
 */

public interface ApiService {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<Object> popularMovies ();

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Observable<Object> highestRatedMovies ();

    @GET("3/movie/{movieId}/videos")
    Observable<Object> trailers (@Path("movieId") String movieId);

    @GET("3/movie/{movieId}/reviews")
    Observable<Object> reviews (@Path("movieId") String movieId);

}
