package org.gmoss.impl.methods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.gmoss.utils.FileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DefaultService implements GMOSSService {

	private Version version;
	private Map<String, Object> defaultHeaders;
	private static final Logger LOG = Logger.getLogger(DefaultService.class);

	private DocumentManager documentManager;

	public Version getVersion() {
		return version;
	}

	public void addDefaultHeaders(HttpServletResponse resp) {
		Set<String> keys = getDefaultHeaders().keySet();
		for (String key : keys) {
			resp.setHeader(key, getDefaultHeaders().get(key).toString());
		}
	}

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		addDefaultHeaders(resp);
		resp.setContentLength(0);
	}

	protected String getClientVersion(Map<String, Object> params)
			throws IOException {
		String version = (String) params.get("method");
		int index = version.indexOf(':');
		if (index == -1) {
			throw new IOException("Unknown client version.");
		}
		return version.substring(index + 1);
	}

	public void flushTemplate(Template templ, Map<String, Object> map,
			HttpServletResponse resp, InputStream source, long length)
			throws TemplateException, IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);
		templ.process(map, pw);
		pw.close();

		resp.setContentLength((int) (bos.size() + length));
		OutputStream os = resp.getOutputStream();
		os.write(bos.toByteArray());
		FileUtils.copy(source, os, (int) length);

		os.close();
	}

	public void flushTemplate(Template templ, Map<String, Object> map,
			HttpServletResponse resp) throws TemplateException, IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);
		templ.process(map, pw);
		pw.close();

		LOG.info(bos);

		resp.setContentLength(bos.size());
		OutputStream os = resp.getOutputStream();
		os.write(bos.toByteArray());
		os.close();
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