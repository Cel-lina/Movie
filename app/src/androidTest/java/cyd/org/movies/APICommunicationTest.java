package cyd.org.movies;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import cyd.org.movies.model.Movie;
import cyd.org.movies.presenter.MoviePresenter;
import cyd.org.movies.presenter.MoviePresenterImpl;

import static junit.framework.Assert.fail;


/**
 * Created by cars on 5/7/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class APICommunicationTest {

  private final CountDownLatch latch = new CountDownLatch(1);

  @Test
  public void testPreferredMovies() {


    MoviePresenter.Listener listener = new MoviePresenter.Listener() {
      @Override
      public void onSuccess(List<Movie> movies) {
        assert (movies != null);
        latch.countDown();
      }


      @Override
      public void onError() {
        fail();
        latch.countDown();
      }
    };
    MoviePresenterImpl moviePresenter = new MoviePresenterImpl(listener);
    moviePresenter.getMovies(MoviePresenter.Criteria.POPULAR);
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }


}
