package cyd.org.movies.views.movieDetailed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cyd.org.movies.common.APIDefinition;
import cyd.org.movies.model.Movie;
import cyd.org.movies.R;
import cyd.org.movies.model.ResultReview;
import cyd.org.movies.presenter.ReviewsMoviePresenter;
import cyd.org.movies.presenter.TrailerPresenter;
import cyd.org.movies.presenter.TrailerPresenterImpl;
import cyd.org.movies.presenter.MoviePresenter;
import cyd.org.movies.presenter.MoviePresenterImpl;

public class MoviesDetailedInformationActivity extends AppCompatActivity  {

  private ImageView imageView;
  private TextView overviewTextView;
  private TextView releaseDate;
  private TextView voteAverage;
  private ImageButton trackImageButton;
  private TrackImageButton imageButtonTracks[];
  private MovieDetailedViewModel movieDetailedViewModel;
  private ImageButton favouriteImageButton ;

  private TrailerPresenter trailerPresenter;
  private MoviePresenter moviePresenter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies_detailed_information);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Bundle b = getIntent().getExtras();
    Movie movie = b.getParcelable("Movie");
    trailerPresenter = new TrailerPresenterImpl();
    moviePresenter = new MoviePresenterImpl(new MoviePresenter.Listener() {
      @Override
      public void onSuccess(List<Movie> movies) {

      }

      @Override
      public void onError() {

      }
    });

    movieDetailedViewModel = new MovieDetailedViewModel();


    if (movie != null) {
      initializeFields(movie);
      movieDetailedViewModel.fetchReview(movie, new ReviewsMoviePresenter.Listener() {
        @Override
        public void onSuccess(List<ResultReview> resultReview) {
          movieDetailedViewModel.initializeReviews(resultReview, (LinearLayout)findViewById(R.id.reviewLinearLayout) , getBaseContext());
        }

        @Override
        public void onError() {

        }
      });
    }

  }

  private void initializeFields(final Movie movie) {
    trackImageButton = (TrackImageButton) findViewById(R.id.trackButton);
    favouriteImageButton = (ImageButton) findViewById(R.id.favouriteButton);
    movieDetailedViewModel.changeBackgroundColor(favouriteImageButton, movie);
    favouriteImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        moviePresenter.changeFavouriteStatus(movie);
        movieDetailedViewModel.changeBackgroundColor((ImageButton)view, movie);
      }
    });
    movieDetailedViewModel.changeBackgroundColor(favouriteImageButton, movie);
    trailerPresenter.getYoutubeLink(String.valueOf(movie.getId()), new TrailerPresenter.Listener() {
      @Override
      public void onSuccess(String[] urlTrailers ) {
        initializeTrackButton(urlTrailers);
      }

      @Override
      public void onError() {
        // TODO error handling
      }
    });
    getSupportActionBar().setTitle(movie.getOriginal_title());
    imageView = (ImageView) findViewById(R.id.thumbnail_film);
    overviewTextView = (TextView) findViewById(R.id.description_film);
    releaseDate = (TextView) findViewById(R.id.release_date);
    voteAverage = (TextView) findViewById(R.id.vote_average);
    overviewTextView.setText(movie.getOverview());
    releaseDate.setText(movie.getRelease_date());

    voteAverage.setText(Float.toString(movie.getVote_average()));
    Picasso.with(this.getApplicationContext()).load(APIDefinition.IMAGE_ENDPOINT + movie.getPoster_path()).fit().centerCrop().into(imageView);
  }

  private void initializeTrackButton(final String [] urlTrailers){
    if ( urlTrailers != null && urlTrailers.length > 0){
      imageButtonTracks = new TrackImageButton[urlTrailers.length];
      for ( int i=0; i<urlTrailers.length ; i++){
        TrackImageButton aux = new TrackImageButton(this.getApplicationContext(), urlTrailers[i]);
        aux.setImageResource(R.drawable.ic_info_black_24dp);
        aux.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivity(  new Intent(Intent.ACTION_VIEW, Uri.parse( ((TrackImageButton)view).getUrlTrack() )) );
          }
        });
        imageButtonTracks[i] = aux;
      }
    }

    LinearLayout parent =( LinearLayout ) trackImageButton.getParent();
    for ( int i=0 ; i<  imageButtonTracks.length ; i++){
      parent.addView(imageButtonTracks[i]);
    }

  }

}
