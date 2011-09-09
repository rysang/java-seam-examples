package org.spring.jsf.app.rest;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.spring.jsf.app.dao.authentication.services.api.AuthorityDao;
import org.spring.jsf.app.dao.authentication.services.api.UserDao;
import org.spring.jsf.app.dao.authentication.user.AppAuthority;
import org.spring.jsf.app.dao.authentication.user.AppUser;
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

  public DefaultDatabase() {

  }

  @GET
  @Produces("text/html")
  public void createDefaults() {

    AppAuthority auth1 = new AppAuthority("ROLE_ADMIN");
    AppAuthority auth2 = new AppAuthority("ROLE_USER");

    authorityService.save(auth1);
    authorityService.save(auth2);

    AppUser moez = new AppUser();
    moez.setUsername("moez");
    moez.setPassword("moez");
    moez.setAppAuthorities(Arrays.asList(new AppAuthority[] { auth1, auth2 }));

    userService.save(moez);
  }
}
