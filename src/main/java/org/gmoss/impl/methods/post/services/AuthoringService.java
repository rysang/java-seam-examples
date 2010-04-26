package org.gmoss.impl.methods.post.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gmoss.impl.methods.DefaultService;
import org.gmoss.utils.PathUtils;
import org.gmoss.utils.VermeerUtil;
import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AuthoringService extends DefaultService {
	private String serverUrl;

	private static Logger LOG = Logger.getLogger(AuthoringService.class);

	public AuthoringService() {
	}

	public String getServerUrl(HttpServletRequest req) {
		if (serverUrl == null) {
			serverUrl = PathUtils.getServerUrl(req);
		}
		return serverUrl;
	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		addDefaultHeaders(resp);

		Map<String, Object> params = VermeerUtil.parseRequest(req);
		String method = (String) params.get("method");

		LOG.info(params);

		try {
			if (method.startsWith("open service")) {
				handleOpenService(req, resp, params);
			} else if (method.startsWith("getDocsMetaInfo")) {
				handleGetDocsMetaInfo(req, resp, params);
			} else if (method.startsWith("list documents")) {
				handleListDocuments(req, resp, params);
			} else if (method.startsWith("checkout document")) {
				handleCheckoutDocument(req, resp, params);
			} else if (method.startsWith("uncheckout document")) {
				handleCheckoutDocument(req, resp, params);
			} else if (method.startsWith("get document")) {
				handleCheckoutDocument(req, resp, params);
			} else if (method.startsWith("put document")) {
				handleCheckoutDocument(req, resp, params);
			} else if (method.startsWith("remove documents")) {
				handleListDocuments(req, resp, params);
			} else if (method.startsWith("move document")) {
				handleCheckoutDocument(req, resp, params);
			} else if (method.startsWith("create url-directories")) {
				handleCreateUrlDirectories(req, resp, params);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void handleCreateUrlDirectories(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {
		// TODO Auto-generated method stub

	}

	private void handleCheckoutDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleListDocuments(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleGetDocsMetaInfo(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleOpenService(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		Template templ = TemplateFactory
				.getTemplate("POST_Authoring_open_service.ftl");
		HashMap<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("clientVersion", getClientVersion(params));
		finalMap.put("serverUrl", getServerUrl(req));
		finalMap.put("serverVersion", getVersion().toString());
		finalMap.put("serverName", "GMOSS");
		finalMap.put("currentUser", getCurrentUser(req));
		finalMap.put("serviceName", params.get("service_name"));

		try {
			flushTemplate(templ, finalMap, resp);
		} catch (TemplateException e) {
			throw new ServletException(e);
		}
	}

	protected String getCurrentUser(HttpServletRequest req) {
		Object user = req.getSession().getAttribute("currentUser");
		return user == null ? "none" : user.toString();
	}
}
