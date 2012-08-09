package eu.cec.sanco.beans;

public class SubmissionElement {
  private Submission submission;

  public Submission getSubmission() {
    return submission;
  }

  public void setSubmission(Submission submission) {
    this.submission = submission;
  }

  @Override
  public String toString() {
    return getSubmission() == null ? "null" : getSubmission().toString();
  }
}
