package eu.cec.sanco.beans;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
  private List<SubmissionElement> submissions;

  public SearchResult() {

  }

  public List<SubmissionElement> getSubmissions() {
    if (submissions == null) {
      submissions = new ArrayList<SubmissionElement>();
    }
    return submissions;
  }

  public void setSubmissions(List<SubmissionElement> submissions) {
    this.submissions = submissions;
  }

  @Override
  public String toString() {
    return getSubmissions().toString();
  }

}
