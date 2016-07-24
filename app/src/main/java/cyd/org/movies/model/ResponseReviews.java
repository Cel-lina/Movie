package cyd.org.movies.model;

import java.util.List;

/**
 * Created by cars on 7/24/16.
 */
public class ResponseReviews {

  private List<ResultReview> results;

  public List<ResultReview> getResults() {
    return results;
  }

  public void setResults(List<ResultReview> results) {
    this.results = results;
  }
}
