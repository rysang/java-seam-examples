package ro.penteker.auktion.beans;

public class Submission {
  private String submission_number;
  private String user_id;
  private String submission_date;
  private String number_complaints;

  public Submission() {

  }

  public String getSubmission_number() {
    return submission_number;
  }

  public void setSubmission_number(String submission_number) {
    this.submission_number = submission_number;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getSubmission_date() {
    return submission_date;
  }

  public void setSubmission_date(String submission_date) {
    this.submission_date = submission_date;
  }

  public String getNumber_complaints() {
    return number_complaints;
  }

  public void setNumber_complaints(String number_complaints) {
    this.number_complaints = number_complaints;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    sb.append("submission_number = ").append(submission_number).append(',');
    sb.append("user_id = ").append(user_id).append(',');
    sb.append("submission_date = ").append(submission_date).append(',');
    sb.append("number_complaints = ").append(number_complaints);
    sb.append(']');

    return sb.toString();
  }

}
