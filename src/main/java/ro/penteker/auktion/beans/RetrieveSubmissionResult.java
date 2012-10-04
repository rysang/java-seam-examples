package ro.penteker.auktion.beans;

import java.util.List;



public class RetrieveSubmissionResult  {
  private String code;
  private List<RetrieveSubmission> submission;

  public RetrieveSubmissionResult() {

  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setSubmission(List<RetrieveSubmission> submission) {
    this.submission = submission;
  }

  public List<RetrieveSubmission> getSubmission() {
    return submission;
  }
}
