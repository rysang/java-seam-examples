package org.gmoss.impl.methods;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.service.GMOSSService;
import org.gmoss.api.service.Version;

public class GETService implements GMOSSService {

	private Version version;
	private Map<String, Object> defaultHeaders;

	public Version getVersion() {
		return version;
	}

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Set<String> keys = getDefaultHeaders().keySet();
		for (String key : keys) {
			resp.setHeader(key, getDefaultHeaders().get(key).toString());
		}

		resp.setContentLength(0);

	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public void setDefaultHeaders(Map<String, Object> defaultHeaders) {
		this.defaultHeaders = defaultHeaders;
	}

	public Map<String, Object> getDefaultHeaders() {
		return defaultHeaders;
	}

	@Override
	public String toString() {
		return getDefaultHeaders().toString();
	}

}
