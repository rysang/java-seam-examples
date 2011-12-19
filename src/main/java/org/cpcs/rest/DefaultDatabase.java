package org.cpcs.rest;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/defaults")
@Component
@Scope("application")
public class DefaultDatabase {

  private boolean done;

  public DefaultDatabase() {
    done = false;
  }

  @GET
  @Produces("text/html")
  public Response createDefaults() throws Exception {

    ResponseBuilder r = Response.temporaryRedirect(new URI("/secure/index.xhtml"));
    r.build();

    return r.build();
  }
}
