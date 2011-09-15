package org.cpcs.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Variant;

import org.cpcs.dao.authentication.services.api.AuthorityDao;
import org.cpcs.dao.authentication.services.api.UserDao;
import org.cpcs.dao.authentication.user.AppAuthority;
import org.cpcs.dao.authentication.user.AppUser;
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
      AppAuthority adminRole = new AppAuthority("ROLE_ADMIN");
      AppAuthority userRole = new AppAuthority("ROLE_USER");

      authorityService.save(adminRole);
      authorityService.save(userRole);

      AppUser moez = new AppUser();
      moez.setUsername("moez");
      moez.setPassword("moez");
      moez.setAppAuthorities(Arrays.asList(new AppAuthority[] { adminRole, userRole }));

      AppUser gigi = new AppUser();
      gigi.setUsername("gigi");
      gigi.setPassword("gigi");
      gigi.setAppAuthorities(Arrays.asList(new AppAuthority[] { userRole }));

      userService.save(moez);
      userService.save(gigi);
      
      done = true;
    }
    
    ResponseBuilder r = Response.temporaryRedirect(new URI("/secure/index.xhtml"));
    r.build();

    return r.build();
  }
}
