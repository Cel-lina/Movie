package cyd.org.movies.common;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by cars on 7/17/16.
 */
public class MovieApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    RealmConfiguration config = new RealmConfiguration.Builder(this).build();
    Realm.setDefaultConfiguration(config);
  }
}
