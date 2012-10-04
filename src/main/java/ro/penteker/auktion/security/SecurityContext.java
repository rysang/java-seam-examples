package ro.penteker.auktion.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContext {

  public static final UserDetails getCurrentUser() {
    return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}