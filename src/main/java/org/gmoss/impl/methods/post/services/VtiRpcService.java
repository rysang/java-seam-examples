package org.gmoss.impl.methods.post.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gmoss.impl.methods.DefaultService;
import org.gmoss.utils.Path;
import org.gmoss.utils.PathUtils;
import org.gmoss.utils.VermeerUtil;
import org.gmoss.utils.templates.TemplateFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class VtiRpcService extends DefaultService {

	public VtiRpcService() {

	}

	@Override
	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		addDefaultHeaders(resp);

		Map<String, Object> params = VermeerUtil.parseRequest(req);
		String method = (String) params.get("method");

		if (method != null) {
			if (method.startsWith("server version")) {
				handleServerVersion(req, resp, params);
			} else if (method.startsWith("url to web url")) {
				handleUrlToWebUrlMethod(req, resp, params);
			}
		}
	}

	private void handleServerVersion(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		Template tmp = TemplateFactory
				.getTemplate("POST_VtiRpcService_server_version.ftl");

		HashMap<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("clientVersion", getClientVersion(params));
		finalMap.put("majorVersion", getVersion().getMajor());
		finalMap.put("minorVersion", getVersion().getMinor());
		finalMap.put("phaseVersion", getVersion().getPhase());
		finalMap.put("incrVersion", getVersion().getIncrement());

		try {
			flushTemplate(tmp, finalMap, resp);
		} catch (TemplateException e) {
			throw new ServletException(e);
		}

	}

	private void handleUrlToWebUrlMethod(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		Template tmp = TemplateFactory
				.getTemplate("POST_VtiRpcService_url_to_web_url.ftl");
		Path path = PathUtils.parsePath(req, params);

		HashMap<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("clientVersion", getClientVersion(params));
		finalMap.put("webUrl", path.getWebUrl());
		finalMap.put("fileUrl", path.getFileUrl());

		try {
			flushTemplate(tmp, finalMap, resp);
		} catch (TemplateException e) {
			throw new ServletException(e);
		}
	}
}
