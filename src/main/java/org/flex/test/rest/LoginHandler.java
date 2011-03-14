package org.flex.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.flex.test.rest.login.FailedResult;
import org.flex.test.rest.login.Result;

@Path("/login")
public class LoginHandler {
	private static final Logger LOG = Logger.getLogger(LoginHandler.class);

	//@GET
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public Result getStatus() {
		FailedResult result = new FailedResult();
		result.setStatus(FailedResult.RESULT_FAILED);
		result.setMessage("Failed to get resource.");

		return result;
	}
}
