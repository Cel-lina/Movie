package cyd.org.movies.presenter;

import java.util.List;

import cyd.org.movies.model.Movie;
import io.realm.Realm;

/**
 * Created by cars on 7/17/16.
 */
public class FavoritesRepository {


  public void addFavouriteMovie(Movie movie){
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    movie.setFavourite_movie(true);
    realm.copyToRealmOrUpdate(movie);


    realm.commitTransaction();
    realm.close();
  }

  public void removeFavouriteMovie ( Movie movie) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    Movie movie1 = realm.where(Movie.class).equalTo("id", movie.getId()).findFirst();
    if ( movie1 != null){
      movie1.setFavourite_movie(false);
    }

    realm.commitTransaction();
    realm.close();
  }

  public List<Movie> getFavoriteMovies (){
    Realm realm = Realm.getDefaultInstance();
    return realm.where(Movie.class).equalTo("favourite_movie", true).findAll();
  }

  public boolean isMovieFavourite ( Movie movie){
    Realm realm = Realm.getDefaultInstance();
    Movie movie1 =   realm.where(Movie.class).equalTo("id", movie.getId()).equalTo("favourite_movie" ,true).findFirst()   ;
    if ( movie1 != null) {
      return true;
    }
    else {
      return false;
    }
  }
}
