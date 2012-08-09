package eu.cec.sanco.presentation.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import eu.cec.sanco.beans.Complaint;
import eu.cec.sanco.beans.ComplaintSet;
import eu.cec.sanco.beans.Entry;
import eu.cec.sanco.services.api.PersistenceService;
import eu.cec.sanco.utils.api.Utils;

@Component
@Scope("session")
@Qualifier("complaintActions")
public class ComplaintActions implements Serializable {

  private List<Entry> complaints;
  private List<Entry> lockedComplaints;
  private static final transient Logger LOG = Logger.getLogger(ComplaintActions.class);

  @Autowired
  private transient PersistenceService persistenceService;

  @Autowired
  private transient Utils utils;

  @Autowired
  private transient UserDetails userDetails;

  private Entry currentEntry;

  public ComplaintActions() {

  }

  public List<Entry> getUnlockedComplaints() {
    if (complaints == null) {
      complaints = persistenceService.getUnlockedComplaints();
    }

    return complaints;
  }

  public List<Entry> getLockedComplaints() {
    if (lockedComplaints == null) {
      lockedComplaints = persistenceService.getLockedComplaints();
    }

    return lockedComplaints;
  }

  public String transfComplTypes(ComplaintSet complaintSet) {

    StringBuilder types = new StringBuilder();
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "appmsg");

    types.append("<ul>");
    for (Complaint c : complaintSet.getComplaints()) {
      String msg = "";

      try {
        msg = bundle.getString("levels." + utils.rebuildString(c.getLevel_1()) + ".value");
      } catch (Exception e) {
        LOG.warn(e);
      }

      types.append("<li>").append(msg).append("</li>");
    }
    types.append("</ul>");

    return types.toString();
  }

  public String createComplaint() {
    currentEntry = new Entry();
    currentEntry.getComplaintSet().setOrganisation_id(userDetails.getUsername());
    currentEntry.getComplaintSet().setReference(String.valueOf(UUID.randomUUID().hashCode()).substring(3));

    currentEntry.getComplaintSet().getComplaints().add(new Complaint());
    return "editcreate-complaint";
  }

  public String editComplaint(Entry entry) {
    currentEntry = entry;
    return "editcreate-complaint";
  }

  public String saveComplaint() {
    if (currentEntry.getId() != null) {
      LOG.info("Creating entry.");
      currentEntry.setId(UUID.randomUUID().toString());
      currentEntry.setTimestamp(new Date());

      persistenceService.saveEntry(currentEntry);
    } else {
      LOG.info("Updating entry.");

      currentEntry.setTimestamp(new Date());
      persistenceService.updateEntry(currentEntry);
    }
    return "home";
  }

  public String close() {

    return "home";
  }

  public void addComplaint() {
    LOG.info("Adding new Complaint");
    currentEntry.getComplaintSet().getComplaints().add(new Complaint());
  }

  public void deleteComplaint(Complaint complaint) {
    LOG.info("Deleting complaint.");
    currentEntry.getComplaintSet().getComplaints().remove(complaint);
  }

  public void setCurrentEntry(Entry currentEntry) {
    this.currentEntry = currentEntry;
  }

  public Entry getCurrentEntry() {
    return currentEntry;
  }
}
