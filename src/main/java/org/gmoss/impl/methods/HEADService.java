package org.gmoss.impl.methods;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.api.document.Document;

public class HEADService extends DefaultService {

	public HEADService() {

	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		addDefaultHeaders(resp);
		try {
			Document doc = getDocumentManager()
					.getDocument(req.getRequestURI());
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}

}
