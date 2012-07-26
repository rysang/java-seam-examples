package org.test.app;

import java.util.Vector;

import javax.media.CaptureDeviceManager;
import javax.media.format.AudioFormat;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class MediaTest {
  
  private static final Logger LOG = Logger.getLogger(MediaTest.class);

  @SuppressWarnings("rawtypes")
  @Test
  public void testMedia() throws Exception {
    LOG.info(System.getProperty("java.class.path"));
    
    Vector devList = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR, 44100, 16, 2));
    Assert.assertNotSame(devList.size(), 0);
  }
}
