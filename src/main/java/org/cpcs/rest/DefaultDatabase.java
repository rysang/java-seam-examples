package org.cpcs.rest;

import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cpcs.dao.NotifRole;
import org.cpcs.dao.NotifUser;
import org.cpcs.dao.authentication.services.api.AuthorityDao;
import org.cpcs.dao.authentication.services.api.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/defaults")
@Component
@Scope("application")
public class DefaultDatabase {

  @Autowired
  private AuthorityDao authorityService;

  @Autowired
  private UserDao userService;

  private boolean done;

  public DefaultDatabase() {
    done = false;
  }

  @GET
  @Produces("text/html")
  public Response createDefaults() throws Exception {
    if (!done) {
      NotifRole adminRole = new NotifRole("ROLE_ADMIN");
      NotifRole userRole = new NotifRole("ROLE_USER");

      authorityService.save(adminRole);
      authorityService.save(userRole);

      NotifUser moez = new NotifUser();
      moez.setUsername("moez");
      moez.setPassword("moez");
      moez.setNotifRoleList((Arrays.asList(new NotifRole[] { adminRole, userRole })));

      NotifUser gigi = new NotifUser();
      gigi.setUsername("gigi");
      gigi.setPassword("gigi");
      gigi.setNotifRoleList((Arrays.asList(new NotifRole[] { userRole })));

      userService.save(moez);
      userService.save(gigi);

      done = true;
    }

    ResponseBuilder r = Response.temporaryRedirect(new URI("/secure/index.xhtml"));
    r.build();

    return r.build();
  }
}
