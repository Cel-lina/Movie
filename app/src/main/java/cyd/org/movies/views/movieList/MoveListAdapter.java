package cyd.org.movies.views.movieList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cyd.org.movies.common.APIDefinition;
import cyd.org.movies.model.Movie;
import cyd.org.movies.views.movieDetailed.MoviesDetailedInformationActivity;
import cyd.org.movies.R;
import cyd.org.movies.views.movieDetailed.MovieDetailFragment;

/**
 * Created by cars on 5/8/16.
 */
public class MoveListAdapter extends RecyclerView.Adapter<ImageViewHolder> {

  private List<Movie> movieList;
  private Context context;
  private boolean mTwoPane;
  private AppCompatActivity instanceMainActivity;

  public MoveListAdapter(boolean mTwoPane, AppCompatActivity instanceMainActivity) {
    Log.d("debug", "initializing movie list adapter");
    movieList = new ArrayList<>(100);
    this.mTwoPane = mTwoPane;
    this.instanceMainActivity = instanceMainActivity;
  }

  public void setMovieList(List<Movie> movieList) {
    this.movieList = movieList;
  }

  @Override
  public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies_image, null);
    context = parent.getContext();
    Log.d("debug", "inflating row");

    return new ImageViewHolder(layoutView);
  }

  @Override
  public void onBindViewHolder(ImageViewHolder holder, final int position) {
    Picasso.with(context).load(APIDefinition.IMAGE_ENDPOINT + movieList.get(position).getPoster_path()).fit().centerCrop().into(holder.movieImageView);
    holder.movieImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("debug", "click");

        if (mTwoPane) {
            Bundle arguments = new Bundle();
          if ( movieList != null && movieList.get(position)!= null) {
              arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID, movieList.get(position));
            }
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            instanceMainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment)
                .commit();

        } else {
          Intent intent = new Intent(context, MoviesDetailedInformationActivity.class);
          if ( movieList != null && movieList.get(position)!= null){
            intent.putExtra("Movie", movieList.get(position));
          }
          context.startActivity(intent);
        }
      }
    });
  }


  @Override
  public int getItemCount() {
    return movieList.size();
  }


}

class ImageViewHolder extends RecyclerView.ViewHolder {

  public final ImageView movieImageView;

  public ImageViewHolder(View itemView) {
    super(itemView);
    movieImageView = (ImageView) itemView.findViewById(R.id.imageTextView);
  }
}