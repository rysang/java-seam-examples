package org.gmoss.impl.methods;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gmoss.api.document.DocumentManager;
import org.gmoss.api.service.GMOSSService;
import org.gmoss.api.service.Version;
import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;

public class GETService implements GMOSSService {

	private Version version;
	private Map<String, Object> defaultHeaders;

	private Logger LOG = Logger.getLogger(GETService.class);

	private DocumentManager documentManager;

	private String index;

	public Version getVersion() {
		return version;
	}

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Set<String> keys = getDefaultHeaders().keySet();
		for (String key : keys) {
			resp.setHeader(key, getDefaultHeaders().get(key).toString());
		}

		try {
			if (req.getRequestURI().equals(index)) {
				Template template = TemplateFactory
						.getTemplate("_vti_inf.html.ftl");
				PrintWriter pw = resp.getWriter();
				template.process(null, pw);
				pw.close();

				return;
			}
		} catch (Exception e) {
			LOG.error("An erro occured.", e);
		}

		resp.sendError(HttpServletResponse.SC_NOT_FOUND);

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

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		return index;
	}

}
