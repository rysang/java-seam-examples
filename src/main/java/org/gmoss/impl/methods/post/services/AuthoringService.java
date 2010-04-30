package org.gmoss.impl.methods.post.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gmoss.api.document.Document;
import org.gmoss.impl.methods.DefaultService;
import org.gmoss.utils.FileUtils;
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
				handleUncheckoutDocument(req, resp, params);
			} else if (method.startsWith("get document")) {
				handleGetDocument(req, resp, params);
			} else if (method.startsWith("put document")) {
				handlePutDocument(req, resp, params);
			} else if (method.startsWith("remove documents")) {
				handleListDocuments(req, resp, params);
			} else if (method.startsWith("move document")) {
				handleMoveDocument(req, resp, params);
			} else if (method.startsWith("create url-directories")) {
				handleCreateUrlDirectories(req, resp, params);
			}
		} catch (Exception e) {
			throw new ServletException("Error", e);
		}
	}

	private void handleUncheckoutDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleMoveDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleGetDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		String path = params.get("service_name")
				+ (String) params.get("document_name");
		Document doc = getDocumentManager().getDocument(path);

		HashMap<String, Object> finalMap = new HashMap<String, Object>();
		String clientVersion = getClientVersion(params);

		finalMap.put("serverVersion", getVersion());
		finalMap.put("clientVersion", clientVersion);
		finalMap.put("etag", doc.getProperty("uid"));
		finalMap.put("docPath", path);
		finalMap.put("docTitle", doc.getName());
		finalMap.put("docSize", doc.getProperty("fileSize"));

		finalMap.put("contentType", doc.getProperty("contentType"));

		Template templ = TemplateFactory
				.getTemplate("POST_Authoring_get_document.ftl");

		InputStream source = doc.getInputStream();
		try {
			flushTemplate(templ, finalMap, resp, source, Integer.valueOf(doc
					.getProperty("fileSize")));

		} catch (Exception e) {
			throw new ServletException(e.getMessage(), e);
		}

		if (source != null)
			source.close();
	}

	private void handlePutDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		Map<String, Object> finalMap = new HashMap<String, Object>();
		Document parentDoc = getDocumentManager().getDocument(
				(String) params.get("service_name"));

		Document doc;
		String name = getDocumentName(params);

		try {
			doc = getDocumentManager().createDocument(parentDoc, name);
		}

		catch (IOException e) {
			String overwrite = (String) params.get("put_option");
			if (overwrite != null && overwrite.contains("overwrite")) {
				LOG.info("File already exists ... overwriting");
				doc = getDocumentManager().getDocument(parentDoc, name);
			} else {
				throw new ServletException("Error", e);
			}
		}

		int fileLength = (Integer) params.get("lengthLeft");

		OutputStream os = doc.getOutputStream();
		InputStream is = (InputStream) params.get("inputStream");
		FileUtils.copy(is, os, fileLength);
		is.close();
		os.close();

		finalMap.put("clientDoc", doc.getName());
		finalMap.put("serverDoc", doc.getName());
		finalMap.put("etag", doc.getProperty("uid"));
		finalMap.put("docSize", fileLength);
		finalMap.put("serverVersion", getVersion());
		finalMap.put("clientVersion", getClientVersion(params));
		finalMap.put("currentUser", getCurrentUser(req));

		Template templ = TemplateFactory
				.getTemplate("POST_Authoring_put_document.ftl");
		try {
			flushTemplate(templ, finalMap, resp);
		} catch (TemplateException e) {
			throw new ServletException(e.getMessage(), e);
		}

	}

	private void handleCreateUrlDirectories(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleCheckoutDocument(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleListDocuments(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params) {

	}

	private void handleGetDocsMetaInfo(HttpServletRequest req,
			HttpServletResponse resp, Map<String, Object> params)
			throws IOException, ServletException {

		String[] documents = PathUtils.getUrlList((String) params
				.get("url_list"), req);

		Map<String, Object> finalMap = new HashMap<String, Object>();
		List<Map<String, String>> docMap = new ArrayList<Map<String, String>>();
		List<Map<String, String>> docFolderMap = new ArrayList<Map<String, String>>();
		try {
			for (String d : documents) {
				Document doc = getDocumentManager().getDocument(d);

				Map<String, String> docProps = new HashMap<String, String>();
				docProps.put("path", doc.getPath().substring(1));
				docProps.put("etag", doc.getProperty("uid"));

				if (!doc.isFolder()) {
					docProps.put("title", doc.getName());
					docProps.put("fileSize", doc.getProperty("fileSize"));

					Map<String, String> lockInfo = getDocumentManager()
							.isDocumentLocked(doc);
					docProps.put("user", lockInfo != null ? lockInfo
							.get("user") : "");
					docFolderMap.add(docProps);
					continue;
				}

				docMap.add(docProps);

			}
		} catch (Exception e) {
			finalMap.put("failedUrls", true);
			finalMap.put("furl", PathUtils.getUrlList((String) params
					.get("url_list"), req)[0]);
		}

		finalMap.put("clientVersion", getClientVersion(params));
		finalMap.put("folderishDocs", docFolderMap);
		finalMap.put("docs", docMap);
		finalMap.put("serverVersion", getVersion());
		finalMap.put("author", "gigi");
		finalMap.put("serverName", "GMOSS");

		Template templ = TemplateFactory
				.getTemplate("POST_Authoring_getDocsMetaInfo.ftl");
		try {
			flushTemplate(templ, finalMap, resp);
		} catch (TemplateException e) {
			throw new ServletException(e.getMessage(), e);
		}
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
			throw new ServletException(e.getMessage(), e);
		}
	}

	protected String getCurrentUser(HttpServletRequest req) {
		Object user = req.getSession().getAttribute("currentUser");
		return user == null ? "none" : user.toString();
	}

	protected String getDocumentName(Map<String, Object> params) {
		String docName = ((String) params.get("document"));
		int index2 = docName.indexOf(';');
		int index1 = docName.indexOf('=');
		return docName.substring(index1 + 1, index2);
	}
}
