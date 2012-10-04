package ro.penteker.auktion.beans;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
  private String id;
  private ComplaintSet complaintSet = new ComplaintSet();
  private Date timestamp;
  private transient boolean selected;

  public Entry() {

  }

  public Entry(String id) {
    super();
    this.id = id;
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

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }

  @Override
  public String toString() {
    return String.format("Entry [id=%s, complaintSet=%s, timestamp=%s, selected=%s]", id, complaintSet, timestamp,
        selected);
  }

}
