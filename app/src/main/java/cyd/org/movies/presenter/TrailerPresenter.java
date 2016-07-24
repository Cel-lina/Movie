package cyd.org.movies.presenter;

/**
 * Created by cars on 7/17/16.
 */
public interface TrailerPresenter {

  interface Listener {
    void onSuccess(String [] urlTrailers);

    void onError();
  }

  void getYoutubeLink(String id, Listener listener);
}
