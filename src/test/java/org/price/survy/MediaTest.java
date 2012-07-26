package org.price.survy;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.googlecode.javacv.OpenCVFrameGrabber;

public class MediaTest {

  private static final Logger LOG = Logger.getLogger(MediaTest.class);

  @Test
  public void testMedia() throws Exception {
    LOG.info(System.getProperty("java.library.path"));

    OpenCVFrameGrabber cvFrameGrabber = new OpenCVFrameGrabber(0);
    cvFrameGrabber.start();
  }
}
