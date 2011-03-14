package org.flex.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/status")
public class MyResource {

	private StatusInfoBean statusInfoBean = new StatusInfoBean();
	private static final Logger LOG = Logger.getLogger(MyResource.class);

	public MyResource() {
		statusInfoBean.jobs
				.add(new JobInfoBean("sample.doc", "printing...", 13));
		LOG.info("Init resource.");
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public StatusInfoBean getStatus() {
		return statusInfoBean;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public synchronized void setStatus(StatusInfoBean status) {
		this.statusInfoBean = status;
	}
}
