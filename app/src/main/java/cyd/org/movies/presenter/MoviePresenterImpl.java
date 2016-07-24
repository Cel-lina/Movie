package cyd.org.movies.presenter;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import cyd.org.movies.BuildConfig;
import cyd.org.movies.common.APICommunication;
import cyd.org.movies.model.Movie;
import cyd.org.movies.model.ResponseMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cars on 5/7/16.
 */
public class MoviePresenterImpl implements MoviePresenter {

  private final Listener listener;
  private FavoritesRepository favoritesRepository;

  public MoviePresenterImpl(Listener listener) {
    this.listener = listener;
    favoritesRepository = new FavoritesRepository();
  }

  @Override
  public void getMovies(Criteria criteria) {
    Call<ResponseMovies> call;

    switch (criteria) {
      case POPULAR:
        call = APICommunication.getAPICommunication().getPopularMovies(BuildConfig.API_KEY);
        break;
      case TOP_RATED:
        call = APICommunication.getAPICommunication().getTopRatedMovies(BuildConfig.API_KEY);
        break;
      case FAVOURITES:
        listener.onSuccess( favoritesRepository.getFavoriteMovies() );
      default:

        return;
    }

    call.enqueue(new Callback<ResponseMovies>() {
      @Override
      public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
        List<Movie> list = Arrays.asList(response.body().getResults());
        listener.onSuccess(list);
      }

      @Override
      public void onFailure(Call<ResponseMovies> call, Throwable t) {
        Log.d("error", t.getMessage());
        listener.onError();
      }
    });
  }

  public void changeFavouriteStatus(Movie movie){
    if ( favoritesRepository.isMovieFavourite(movie)){
      favoritesRepository.removeFavouriteMovie(movie);
    }
    else {
      favoritesRepository.addFavouriteMovie(movie);

    }
  }


}
