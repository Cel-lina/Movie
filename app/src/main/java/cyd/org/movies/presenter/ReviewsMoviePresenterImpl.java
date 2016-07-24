package cyd.org.movies.presenter;

import java.util.List;

import cyd.org.movies.BuildConfig;
import cyd.org.movies.common.APICommunication;
import cyd.org.movies.model.ResponseReviews;
import cyd.org.movies.model.ResultReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cars on 7/24/16.
 */
public class ReviewsMoviePresenterImpl implements ReviewsMoviePresenter {
  @Override
  public void fetchReviews(String id , final Listener listener) {

    Call<ResponseReviews> call = APICommunication.getAPICommunication().fetchReviews(id, BuildConfig.API_KEY);
    call.enqueue(new Callback<ResponseReviews>() {
      @Override
      public void onResponse(Call<ResponseReviews> call, Response<ResponseReviews> response) {
        listener.onSuccess((List<ResultReview>) response.body().getResults());
      }

      @Override
      public void onFailure(Call<ResponseReviews> call, Throwable t) {
        listener.onError();
      }
    });


  }
}
