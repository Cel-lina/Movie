package cyd.org.movies.presenter;


import java.util.List;

import cyd.org.movies.model.Movie;

/**
 * Created by cars on 5/7/16.
 */
public interface MoviePresenter {
  enum Criteria {
    POPULAR, TOP_RATED, FAVOURITES
  }

  void getMovies(Criteria criteria);
  void changeFavouriteStatus(Movie movie);


  interface Listener {
    void onSuccess(List<Movie> movies);

    void onError();
  }

}


