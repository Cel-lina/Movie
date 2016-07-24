package cyd.org.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by cars on 5/7/16.
 */
@RealmClass
public class Movie  implements Parcelable , RealmModel {

  private String poster_path;
  private boolean adult;
  private String overview;
  private String release_date;

  @PrimaryKey
  private int id;
  private String original_title;
  private String title;
  private float vote_average;

  private boolean favourite_movie ;

  public Movie(){

  }

  public boolean isFavourite_movie() {
    return favourite_movie;
  }

  public void setFavourite_movie(boolean favourite_movie) {
    this.favourite_movie = favourite_movie;
  }

  public float getVote_average() {
    return vote_average;
  }

  public void setVote_average(float vote_average) {
    this.vote_average = vote_average;
  }

  protected Movie(Parcel in) {
    poster_path = in.readString();
    adult = in.readByte() != 0;
    overview = in.readString();
    release_date = in.readString();
    id = in.readInt();
    original_title = in.readString();
    title = in.readString();
    vote_average = in.readFloat();
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override
    public Movie createFromParcel(Parcel in) {
      return new Movie(in);
    }

    @Override
    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(poster_path);
    dest.writeByte((byte) (adult ? 1 : 0));
    dest.writeString(overview);
    dest.writeString(release_date);
    dest.writeInt(id);
    dest.writeString(original_title);
    dest.writeString(title);
    dest.writeFloat(vote_average);
  }
}
