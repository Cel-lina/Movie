package cyd.org.movies.views.movieDetailed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cyd.org.movies.R;
import cyd.org.movies.common.APIDefinition;
import cyd.org.movies.model.Movie;
import cyd.org.movies.model.ResultReview;
import cyd.org.movies.presenter.ReviewsMoviePresenter;
import cyd.org.movies.presenter.TrailerPresenter;
import cyd.org.movies.presenter.TrailerPresenterImpl;
import cyd.org.movies.presenter.MoviePresenter;
import cyd.org.movies.presenter.MoviePresenterImpl;
import cyd.org.movies.views.movieList.MovieListActivity;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a
 * on handsets.
 */
public class MovieDetailFragment extends Fragment{
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";
  private Movie movie;

  private ImageView imageView;
  private TextView overviewTextView;
  private TextView releaseDate;
  private TextView voteAverage;
  private ImageButton trackImageButton;
  private TrackImageButton imageButtonTracks[];
  private ImageButton favouriteButton;

  private TrailerPresenter trailerPresenter;
  private MoviePresenter moviePresenter;
  private MovieDetailedViewModel movieDetailedViewModel = new MovieDetailedViewModel();




  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public MovieDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments()!= null && getArguments().containsKey(MovieDetailFragment.ARG_ITEM_ID)) {
      // Load the dummy content specified by the fragment
      // arguments. In a real-world scenario, use a Loader
      // to load content from a content provider.

      Movie movie = getArguments().getParcelable(MovieDetailFragment.ARG_ITEM_ID);
      this.movie = movie;

    }
    trailerPresenter = new TrailerPresenterImpl();
    moviePresenter = new MoviePresenterImpl(new MoviePresenter.Listener() {
      @Override
      public void onSuccess(List<Movie> movies) {

      }

      @Override
      public void onError() {
        // todo error handling
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.activity_movies_detailed_information, container, false);
    if ( movie != null) initializeFields(movie,rootView);
    return rootView;
  }

  private void initializeFields(final Movie movie, View rootview) {
    trackImageButton = (TrackImageButton) rootview.findViewById(R.id.trackButton);
    favouriteButton = (ImageButton) rootview.findViewById(R.id.favouriteButton);
    movieDetailedViewModel.changeBackgroundColor(favouriteButton, movie);
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

    favouriteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       moviePresenter.changeFavouriteStatus(movie);
        movieDetailedViewModel.changeBackgroundColor(favouriteButton, movie);
      }
    });

    CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) rootview.findViewById(R.id.toolbar_layout);
      if (appBarLayout != null) {
      appBarLayout.setTitle(movie.getOriginal_title());
    }

    imageView = (ImageView) rootview.findViewById(R.id.thumbnail_film);
    overviewTextView = (TextView) rootview.findViewById(R.id.description_film);
    releaseDate = (TextView) rootview.findViewById(R.id.release_date);
    voteAverage = (TextView) rootview.findViewById(R.id.vote_average);
    overviewTextView.setText(movie.getOverview());
    releaseDate.setText(movie.getRelease_date());

    voteAverage.setText(Float.toString(movie.getVote_average()));
    Picasso.with(getActivity().getApplicationContext()).load(APIDefinition.IMAGE_ENDPOINT + movie.getPoster_path()).fit().centerCrop().into(imageView);

    movieDetailedViewModel.fetchReview(movie, new ReviewsMoviePresenter.Listener() {
      @Override
      public void onSuccess(List<ResultReview> resultReview) {
        movieDetailedViewModel.initializeReviews(resultReview, (LinearLayout)getActivity().findViewById(R.id.reviewLinearLayout) , getContext());
      }

      @Override
      public void onError() {

      }
    });
  }

  private void initializeTrackButton(final String [] urlTrailers){
    if ( urlTrailers != null && urlTrailers.length > 0){
      imageButtonTracks = new TrackImageButton[urlTrailers.length];
      for ( int i=0; i<urlTrailers.length ; i++){
        TrackImageButton aux = new TrackImageButton(this.getContext(), urlTrailers[i]);
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
