package org.spring.jsf.app.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;

@Path("/download")
@Component
@Scope("request")
public class Downloader {
  private static final Logger LOG = Logger.getLogger(Downloader.class);

  @Context
  private UriInfo uriInfo;
  @Context
  private Request request;

  public Downloader() {

  }

  @GET
  @Produces("application/pdf")
  @Path("{id}/{name}")
  @Cacheable(cacheName = "fileCaching")
  public File getStatus(@PathParam("id") String id, @PathParam("name") String name, @Context HttpServletRequest request)
      throws IOException {

    File f = new File("D:", name);
    FileOutputStream fos = new FileOutputStream(f);
    fos.write("hey man wuss up ?".getBytes());
    fos.close();

    return f;
  }
}
