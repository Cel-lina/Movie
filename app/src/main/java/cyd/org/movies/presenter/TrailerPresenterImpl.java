package cyd.org.movies.presenter;

import cyd.org.movies.common.APICommunication;
import cyd.org.movies.model.ResponseTrailers;
import cyd.org.movies.BuildConfig;
import cyd.org.movies.model.ResultTrailer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cars on 7/17/16.
 */
public class TrailerPresenterImpl implements TrailerPresenter {

  private static final String YOUTUBE = "https://www.youtube.com/watch?v=";

  @Override
  public void getYoutubeLink(String id, final Listener listener) {
    Call<ResponseTrailers> call ;
    call = APICommunication.getAPICommunication().fetchTrailer( id , BuildConfig.API_KEY);
    call.enqueue(new Callback<ResponseTrailers>() {
      @Override
      public void onResponse(Call<ResponseTrailers> call, Response<ResponseTrailers> response) {
        if ( response.body() == null){
          listener.onError();
        }
        else {
          String [] trailers = getYoutubeLink(response.body());
          listener.onSuccess(trailers);
        }

      }

      @Override
      public void onFailure(Call<ResponseTrailers> call, Throwable t) {
        listener.onError();
      }
    });
  }


  private String[] getYoutubeLink ( ResponseTrailers responseTrailers){
    if ( responseTrailers != null ){
      String [] links =  new String [responseTrailers.getResults().size()];
      int i=0;
      for ( ResultTrailer aux: responseTrailers.getResults() ){
        links[i] = YOUTUBE + aux.getKey();
        i++;
      }
      return links;
    }
    return null;
  }
}
