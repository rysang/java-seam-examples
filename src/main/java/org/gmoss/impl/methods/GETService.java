package org.gmoss.impl.methods;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;

public class GETService extends DefaultService {

	private String index;

	public GETService() {

	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		addDefaultHeaders(resp);
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
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		return index;
	}

}
