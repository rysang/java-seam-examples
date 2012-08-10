package eu.cec.sanco.presentation.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.TabChangeEvent;
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

  private Entry currentViewEntry;

  private int tabIndex;

  public ComplaintActions() {

  }

  public void onTabChange(TabChangeEvent event) {
    tabIndex = new Integer(event.getTab().getId().substring(3));
    LOG.info("Tab changed to: " + tabIndex);
  }

  public void reset() {
    complaints = null;
    lockedComplaints = null;
  }

  public List<Entry> getUnlockedComplaints(String orgId) {
    if (complaints == null) {
      complaints = persistenceService.getUnlockedComplaints(orgId);
    }

    return complaints;
  }

  public List<Entry> getLockedComplaints(String orgId) {
    if (lockedComplaints == null) {
      lockedComplaints = persistenceService.getLockedComplaints(orgId);
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

  public static final String getNextReference() {
    String ret = String.valueOf(Math.abs(UUID.randomUUID().hashCode()));
    ret = ret.substring(0, 7);
    return ret;
  }

  public String createComplaint() {
    currentEntry = new Entry();
    currentEntry.getComplaintSet().setOrganisation_id(userDetails.getUsername());
    currentEntry.getComplaintSet().setReference(getNextReference());

    currentEntry.getComplaintSet().getComplaints().add(new Complaint());
    return "editcreate-complaint";
  }

  public void deleteSelComplaints() {
    LOG.info("Delete complaints.");
    List<String> ids = new ArrayList<String>(complaints.size());

    for (Entry e : complaints) {
      if (e.isSelected()) {
        ids.add(e.getId());
      }
    }

    persistenceService.removeEntries(ids);
    reset();
  }

  public void deleteComplaint(Entry entry) {
    LOG.info("Delete complaint: " + entry);
    List<String> ids = new ArrayList<String>(1);
    ids.add(entry.getId());

    persistenceService.removeEntries(ids);
    reset();
  }

  public String editComplaint(Entry entry) {
    currentEntry = entry;
    return "editcreate-complaint";
  }

  public String saveComplaint() {
    if (currentEntry == null || currentEntry.getComplaintSet().getComplaints().isEmpty()) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention", "There should be at least one complaint."));
      return null;
    }

    if (currentEntry.getId() == null) {
      LOG.info("Creating entry.");
      currentEntry.setId(UUID.randomUUID().toString());
      currentEntry.setTimestamp(new Date());

      persistenceService.saveEntry(currentEntry);
    } else {
      LOG.info("Updating entry.");

      currentEntry.setTimestamp(new Date());
      persistenceService.updateEntry(currentEntry);
    }

    reset();
    return "home";
  }

  public String close() {
    return "home";
  }

  public String viewEntry(Entry entry) {
    LOG.info("Viewing entry.");
    currentViewEntry = entry;

    return "view-complaint";
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

  public void setCurrentViewEntry(Entry currentViewEntry) {
    this.currentViewEntry = currentViewEntry;
  }

  public Entry getCurrentViewEntry() {
    return currentViewEntry;
  }

  public void setTabIndex(int tabIndex) {
    this.tabIndex = tabIndex;
  }

  public int getTabIndex() {
    return tabIndex;
  }
}
