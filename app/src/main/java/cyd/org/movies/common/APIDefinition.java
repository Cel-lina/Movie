package cyd.org.movies.common;

import cyd.org.movies.model.ResponseMovies;
import cyd.org.movies.model.ResponseReviews;
import cyd.org.movies.model.ResponseTrailers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cars on 5/7/16.
 */
public interface APIDefinition {

  String IMAGE_ENDPOINT = "http://image.tmdb.org/t/p/w185";

  @GET("3/movie/popular")
  Call<ResponseMovies> getPopularMovies(@Query("api_key") String apiKey);

  @GET("3/movie/top_rated")
  Call<ResponseMovies> getTopRatedMovies(@Query("api_key") String apiKey);

  @GET("3/movie/{id}/videos")
  Call <ResponseTrailers> fetchTrailer(@Path("id") String movieId, @Query("api_key") String apiKey);

  @GET("3/movie/{id}/reviews")
  Call <ResponseReviews> fetchReviews(@Path("id") String movieId, @Query("api_key") String apiKey);
}
