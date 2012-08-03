package eu.cec.sanco.presentation.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.cec.sanco.beans.Complaint;
import eu.cec.sanco.beans.ComplaintSet;
import eu.cec.sanco.services.api.PersistenceService;
import eu.cec.sanco.utils.api.Utils;

@Component
@Scope("session")
@Qualifier("complaintActions")
public class ComplaintActions implements Serializable {

  private List<ComplaintSet> complaints;
  private List<ComplaintSet> lockedComplaints;
  private static final transient Logger LOG = Logger.getLogger(ComplaintActions.class);

  @Autowired
  private transient PersistenceService persistenceService;

  @Autowired
  private transient Utils utils;

  public ComplaintActions() {

  }

  public List<ComplaintSet> getUnlockedComplaints() {
    if (complaints == null) {
      complaints = persistenceService.getUnlockedComplaints();
    }

    return complaints;
  }

  public List<ComplaintSet> getLockedComplaints() {
    if (lockedComplaints == null) {
      lockedComplaints = persistenceService.getLockedComplaints();
    }

    return lockedComplaints;
  }

  public void setComplaints(List<ComplaintSet> complaints) {
    this.complaints = complaints;
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

}
