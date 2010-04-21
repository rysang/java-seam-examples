package org.gmoss.impl.methods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.document.Document;
import org.gmoss.api.document.DocumentManager;
import org.gmoss.api.service.GMOSSService;
import org.gmoss.api.service.Version;
import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;

public class PROPFINDService implements GMOSSService {

	private Version version;
	private Map<String, Object> defaultHeaders;

	private DocumentManager documentManager;

	private Template folderTemplate;

	public Version getVersion() {
		return version;
	}

	public PROPFINDService() throws IOException {
		folderTemplate = TemplateFactory.getTemplate("PROPFIND_directory.ftl");
	}

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		Document doc = null;
		try {
			doc = documentManager.getDocument(req.getRequestURI());
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try {
			if (doc.isFolder()) {
				HashMap<String, Object> rootMap = new HashMap<String, Object>();
				rootMap.put("href", req.getRequestURL());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd'T'hh:mm:ss'Z'");
				rootMap.put("lastModified", format
						.format(doc.getLastModified()));
				rootMap.put("uid", doc.getProperty("uid"));

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintWriter pw = new PrintWriter(bos);
				folderTemplate.process(rootMap, pw);
				pw.close();

				resp.sendError(207);
				Set<String> keys = getDefaultHeaders().keySet();
				for (String key : keys) {
					resp
							.setHeader(key, getDefaultHeaders().get(key)
									.toString());
				}
				resp.setContentLength(bos.size());
				OutputStream os = resp.getOutputStream();
				os.write(bos.toByteArray());
				os.close();
			}

		} catch (Exception e) {
			throw new ServletException(e);
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
