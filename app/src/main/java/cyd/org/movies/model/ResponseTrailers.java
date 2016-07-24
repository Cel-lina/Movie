package cyd.org.movies.model;

import java.util.List;

/**
 * Created by cars on 7/17/16.
 */
public class ResponseTrailers {

  private String id;
  private List <ResultTrailer> results;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<ResultTrailer> getResults() {
    return results;
  }

  public void setResults(List<ResultTrailer> results) {
    this.results = results;
  }
}
