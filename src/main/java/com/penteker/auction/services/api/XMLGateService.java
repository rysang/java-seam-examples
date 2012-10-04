package com.penteker.auction.services.api;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import com.penteker.auction.beans.SearchResult;

import freemarker.template.TemplateException;

public interface XMLGateService {
  public String post(String requestBody) throws ClientProtocolException, IOException;

  public SearchResult searchSubmissions(String year, String organizationId, String country) throws TemplateException,
      IOException, SAXException, ParserConfigurationException;

  public void setWebServicePath(String webServicePath);
}
