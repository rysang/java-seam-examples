package ro.penteker.auktion.presentation.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ro.penteker.auktion.beans.SearchResult;
import ro.penteker.auktion.beans.Submission;
import ro.penteker.auktion.beans.SubmissionElement;
import ro.penteker.auktion.services.api.XMLGateService;
import ro.penteker.auktion.utils.api.Utils;


import freemarker.template.TemplateException;

@Component
@Scope("session")
@Qualifier("submissionActions")
public class SubmissionActions implements Serializable {

  private static final transient Logger LOG = Logger.getLogger(SubmissionActions.class);
  private List<Submission> searchResult;

  @Autowired
  private transient Utils utils;

  @Autowired
  private transient UserDetails userDetails;

  @Autowired
  private transient XMLGateService xmlGateService;

  private String currentYear;
  private String tmpYear;

  public SubmissionActions() {

  }

  public List<Submission> getSubmissions() throws TemplateException, IOException, SAXException,
      ParserConfigurationException {
    if (searchResult == null) {
      SearchResult result = xmlGateService.searchSubmissions(getCurrentYear(), userDetails.getUsername(), userDetails
          .getUsername().substring(0, 2));

      searchResult = new ArrayList<Submission>(100);
      if (result != null) {
        for (SubmissionElement se : result.getSubmissions()) {
          searchResult.add(se.getSubmission());
        }
      }
    }

    return searchResult;
  }

  public void setCurrentYear(String currentYear) {
    this.currentYear = currentYear;
  }

  public void handleYearChange() {
    LOG.info(currentYear);
    if (!String.valueOf(currentYear).equals(tmpYear)) {
      searchResult = null;
    }

    tmpYear = currentYear;
  }

  public String getCurrentYear() {
    if (currentYear == null) {
      currentYear = String.valueOf(new Date().getYear() + 1900);
    }
    return currentYear;
  }

}
