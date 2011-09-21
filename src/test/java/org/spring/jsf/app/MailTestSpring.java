package org.spring.jsf.app;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

@ContextConfiguration(locations = { "classpath:mail-context.xml" })
public class MailTestSpring extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(MailTestSpring.class);

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private SimpleMailMessage mailMessage;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testMail() throws Exception {
    LOG.info(mailSender);

    mailSender.send(mailMessage);
  }

}
