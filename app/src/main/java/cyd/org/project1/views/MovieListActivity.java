package cyd.org.project1.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import cyd.org.project1.R;
import cyd.org.project1.model.Movie;
import cyd.org.project1.presenter.MoviePresenter;
import cyd.org.project1.presenter.MoviePresenterImpl;
import cyd.org.project1.views.movieList.MoveListAdapter;
import cyd.org.project1.views.settings.SettingsActivity;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements MoviePresenter.Listener, SharedPreferences.OnSharedPreferenceChangeListener  {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;

  private GridLayoutManager gridLayoutManager;
  private RecyclerView recyclerView;
  private MoveListAdapter moveListAdapter;
  private MoviePresenter moviePresenter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());



    if (findViewById(R.id.movie_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }
    moviePresenter = new MoviePresenterImpl(this);
    moviePresenter.getMovies(MoviePresenter.Criteria.POPULAR);
    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    initializeList();
  }


  private void initializeList() {
    gridLayoutManager = new GridLayoutManager(this, 2);
    moveListAdapter = new MoveListAdapter(mTwoPane, this);
    recyclerView = (RecyclerView) findViewById(R.id.listImages);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(moveListAdapter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
      startActivity(intent);
      return true;
    }

    if (item.getItemId() == android.R.id.home) {
      finish();  //return to caller
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onSuccess(List<Movie> movies) {
    moveListAdapter.setMovieList(movies);
    moveListAdapter.notifyDataSetChanged();
    Log.d("debug", "loading movies again");
  }


  @Override
  public void onError() {
    // TODO show an error
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Log.d("debug", "settings changed" + key);
    String criteria = sharedPreferences.getString(key, "0");
    MoviePresenter.Criteria finalCriteria = MoviePresenter.Criteria.POPULAR;
    switch (criteria) {
      case "0":
        finalCriteria = MoviePresenter.Criteria.POPULAR;
        break;
      case "1":
        finalCriteria = MoviePresenter.Criteria.TOP_RATED;
        break;
    }
    Log.d("debug", "criteria is " + finalCriteria.name());

    moviePresenter.getMovies(finalCriteria);


  }
}
