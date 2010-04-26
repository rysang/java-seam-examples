package org.gmoss.impl.methods;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.service.GMOSSService;

public class POSTService extends DefaultService {

	private GMOSSService vtiRpcService;
	private GMOSSService authoringService;

	public POSTService() {

	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		addDefaultHeaders(resp);

		if (req.getContentType().equals("application/x-www-form-urlencoded")) {

			if (req.getRequestURI().endsWith("/_vti_bin/shtml.dll/_vti_rpc")) {
				vtiRpcService.handleService(req, resp);
			}

			else if (req.getRequestURI().endsWith(
					"/_vti_bin/_vti_aut/author.dll")) {
				authoringService.handleService(req, resp);
			}
		}
	}

	public void setVtiRpcService(GMOSSService vtiRpcService) {
		this.vtiRpcService = vtiRpcService;
	}

	public GMOSSService getVtiRpcService() {
		return vtiRpcService;
	}

	public void setAuthoringService(GMOSSService authoringService) {
		this.authoringService = authoringService;
	}

	public GMOSSService getAuthoringService() {
		return authoringService;
	}

}
