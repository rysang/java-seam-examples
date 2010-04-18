package org.gmoss.api.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gmoss.api.service.GMOSSService;

public class HttpRequestLogger {

	public HttpRequestLogger() {

	}

	public void log(GMOSSService service, HttpServletRequest req,
			HttpServletResponse resp) {
		Logger logger = Logger.getLogger(service.getClass());

		logger.info("-------------------------------------------------");
		logger.info(req);
		logger.info("-------------------------------------------------");
		logger.info(resp);
		logger.info("-------------------------------------------------");
	}
}
