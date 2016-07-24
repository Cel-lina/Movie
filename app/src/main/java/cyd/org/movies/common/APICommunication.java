package cyd.org.movies.common;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

import cyd.org.movies.BuildConfig;
import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cars on 5/7/16.
 */
public class APICommunication {
  private static APICommunication instance;
  private static APIDefinition service;


  public static APIDefinition getAPICommunication() {
    if (instance == null) {
      instance = new APICommunication();
    }
    return service;
  }

  private APICommunication() {

     /* It is necessary in order to ignore realm annotations. Check realm.io documentation for more info */
    GsonBuilder gsonBuilder = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaringClass().equals(RealmObject.class);
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    });


    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.URL_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build();



    service = retrofit.create(APIDefinition.class);
  }

}
