package cyd.org.movies.views.movieDetailed;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cyd.org.movies.R;
import cyd.org.movies.model.Movie;
import cyd.org.movies.model.ResultReview;
import cyd.org.movies.presenter.FavoritesRepository;
import cyd.org.movies.presenter.ReviewsMoviePresenter;
import cyd.org.movies.presenter.ReviewsMoviePresenterImpl;

/**
 * Created by cars on 7/24/16.
 */
public class MovieDetailedViewModel {

  private FavoritesRepository repository;
  private ReviewsMoviePresenter reviewsMoviePresenter;


  public void initializeReviews(final List<ResultReview> review, LinearLayout parentLayout, Context context){
    if ( review != null && review.size() > 0){
      for ( int i=0; i<review.size() ; i++){
        TextView header = new TextView(context);
        header.setText(" Author: " + review.get(i).getAuthor());
        header.setTextColor(Color.BLACK);

        TextView reviewContent = new TextView(context);
        reviewContent.setText(review.get(i).getContent());
        reviewContent.setTextColor(Color.GRAY);

        parentLayout.addView(header);
        parentLayout.addView(reviewContent);
      }
    }

  }



  public MovieDetailedViewModel () {
    repository = new FavoritesRepository();
    reviewsMoviePresenter = new ReviewsMoviePresenterImpl();
  }

  public void fetchReview(Movie movie, ReviewsMoviePresenter.Listener listener){
    reviewsMoviePresenter.fetchReviews  (  String.valueOf(movie.getId()) , listener);
  }


  public void changeBackgroundColor (ImageButton imageButton, Movie movie) {
    if ( repository.isMovieFavourite(movie) ) {
      imageButton.setImageResource(R.drawable.ic_stars_black_18dp);
    }
    else {
      imageButton.setImageResource(R.drawable.ic_star_rate_black_18dp);
    }
  }





}
