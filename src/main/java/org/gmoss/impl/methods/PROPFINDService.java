package org.gmoss.impl.methods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.document.Document;
import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;

public class PROPFINDService extends DefaultService {

	private Template folderTemplate;

	public PROPFINDService() throws IOException {
		folderTemplate = TemplateFactory.getTemplate("PROPFIND_directory.ftl");
	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// TODO Remove this
		if (true) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// TODO Remove this

		Document doc = null;
		try {
			doc = getDocumentManager().getDocument(req.getRequestURI());
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

				resp.setStatus(207);
				addDefaultHeaders(resp);
				resp.setContentLength(bos.size());
				OutputStream os = resp.getOutputStream();
				os.write(bos.toByteArray());
				os.close();
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
