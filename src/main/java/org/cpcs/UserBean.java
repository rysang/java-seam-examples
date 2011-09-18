package org.cpcs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userBean")
@Scope("session")
public class UserBean {
  private static final Logger LOG = Logger.getLogger(UserBean.class);

  @Autowired
  protected UserDetails currentUser;

  protected String nickname;
  protected String email;
  protected Date birthday;

  /**
   * @return the nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @param nickname
   *          the nickname to set
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the birthday
   */
  public Date getBirthday() {
    return birthday;
  }

  /**
   * @param birthday
   *          the birthday to set
   */
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  /**
   * stupid simple email validation
   * 
   * @param context
   * @param validated
   * @param value
   */
  public void validateEmail(FacesContext context, UIComponent validated, Object value) {
    // simple stupid validation
    String mail = (String) value;
    if (!mail.matches(".+\\@.+\\..+")) {
      FacesMessage msg = new FacesMessage("This is not an e-mail!");
      throw new ValidatorException(msg);
    }
  }

  public List<String> suggestEmail(String entered) {
    List<String> list = new ArrayList<String>();
    for (int i = 1; i < 11; i++) {
      list.add(entered + i + "@hascode.com");
    }
    return list;
  }

  public String onSubmit() {
    LOG.info("----------------------------");
    LOG.info("CurrentUser: " + currentUser);

    StringBuilder auths = new StringBuilder('[');
    for (GrantedAuthority authority : currentUser.getAuthorities()) {
      auths.append(authority).append(" ,");
    }

    auths.append(']');

    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", currentUser.toString());
    FacesContext.getCurrentInstance().addMessage(null, message);

    LOG.info("Authorities: " + auths);
    LOG.info("----------------------------");
    return null;
  }
}
