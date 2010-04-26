package org.gmoss.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PathUtils {
	public static Path parsePath(HttpServletRequest req,
			Map<String, Object> params) {
		Path path = new Path();
		String url = (String) params.get("url");

		path.setWebUrl("/");
		path.setFileUrl(url.substring(1));

		return path;
	}

	public static String getServerUrl(HttpServletRequest req) {
		String host = req.getHeader("Host");
		int index = req.getRequestURL().indexOf(host) + host.length();

		return req.getRequestURL().substring(0, index);
	}
}
