package eu.cec.sanco.beans;

public class RetrieveSubmission {
  private Complaint complaint;

  public RetrieveSubmission() {

  }

  public void setComplaint(Complaint complaint) {
    this.complaint = complaint;
  }

  public Complaint getComplaint() {
    return complaint;
  }
}
