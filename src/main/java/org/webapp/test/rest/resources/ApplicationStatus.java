package org.webapp.test.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.webapp.test.api.TestDAO;
import org.webapp.test.rest.StatusInfoBean;

@Component
@Scope("request")
@Path("/applicationStatus")
public class ApplicationStatus {
  private static final Logger LOG = Logger.getLogger(ApplicationStatus.class);

  @Autowired
  @Qualifier("testDao")
  private TestDAO testDAO;

  public ApplicationStatus() {

  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public StatusInfoBean getStatus(@Context HttpServletRequest request) {
    LOG.info("Get resource. " + testDAO);
    return new StatusInfoBean();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public synchronized void setStatus(StatusInfoBean status) {
    LOG.info("Put resource.");
  }

}
