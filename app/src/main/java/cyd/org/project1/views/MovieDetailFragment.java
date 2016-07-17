package cyd.org.project1.views;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cyd.org.project1.R;
import cyd.org.project1.common.APIDefinition;
import cyd.org.project1.model.Movie;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
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


  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public MovieDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(MovieDetailFragment.ARG_ITEM_ID)) {
      // Load the dummy content specified by the fragment
      // arguments. In a real-world scenario, use a Loader
      // to load content from a content provider.

      Movie movie = getArguments().getParcelable(MovieDetailFragment.ARG_ITEM_ID);
      this.movie = movie;

    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.activity_movies_detailed_information, container, false);
    if ( movie != null) initializeFields(movie,rootView);
    return rootView;
  }

  private void initializeFields(Movie movie, View rootview) {
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
  }
}
