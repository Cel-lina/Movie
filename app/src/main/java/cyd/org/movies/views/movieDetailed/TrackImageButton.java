package cyd.org.movies.views.movieDetailed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by cars on 7/17/16.
 */
public class TrackImageButton extends ImageButton {

  private String urlTrack ;

  public TrackImageButton(Context context, String urlTrack) {
    super(context);
    this.urlTrack = urlTrack;

  }

  public TrackImageButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TrackImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }



  public String getUrlTrack() {
    return urlTrack;
  }
}
