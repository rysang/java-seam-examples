package org.gmoss.impl.methods;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.document.DocumentManager;
import org.gmoss.api.service.GMOSSService;
import org.gmoss.api.service.Version;

public class HEADService implements GMOSSService {

	private Version version;
	private Map<String, Object> defaultHeaders;

	private DocumentManager documentManager;

	public Version getVersion() {
		return version;
	}

	public HEADService() {

	}

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		try {
			documentManager.getDocument(req.getRequestURI());
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
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

	public DocumentManager getDocumentManager() {

		return documentManager;
	}

	public void setDocumentManager(DocumentManager documentManager) {
		this.documentManager = documentManager;
	}

}
