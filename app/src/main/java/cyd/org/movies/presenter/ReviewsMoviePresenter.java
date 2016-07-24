package cyd.org.movies.presenter;

import java.util.List;

import cyd.org.movies.model.ResultReview;

/**
 * Created by cars on 7/24/16.
 */
public interface ReviewsMoviePresenter {

  void fetchReviews(String id, Listener listener);

  interface Listener {
    void onSuccess(List <ResultReview> resultReview);

    void onError();
  }

}
