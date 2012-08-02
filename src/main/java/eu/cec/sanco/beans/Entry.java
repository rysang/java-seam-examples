package eu.cec.sanco.beans;

import java.util.Date;

public class Entry {
  private String id;
  private ComplaintSet complaintSet;
  private Date timestamp;

  public Entry() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ComplaintSet getComplaintSet() {
    return complaintSet;
  }

  public void setComplaintSet(ComplaintSet complaintSet) {
    this.complaintSet = complaintSet;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return String.format("Entry [id=%s, complaintSet=%s, timestamp=%s]", id, complaintSet, timestamp);
  }

}
