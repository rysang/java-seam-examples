package org.gmoss.utils;

import java.io.IOException;
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

	public static final String[] getUrlList(String url_list,
			HttpServletRequest req) throws IOException {

		if (url_list.startsWith("[") && url_list.endsWith("]")) {

			final String[] ret = url_list.substring(1, url_list.length() - 1)
					.split(";");
			if (!ret[0].startsWith("http://") || ret[0].startsWith("https://")) {
				int index = req.getRequestURL().indexOf("_vti_bin");
				final String ctx = req.getRequestURL().substring(0, index);

				for (int i = 0; i < ret.length; i++) {
					ret[i] = ctx + ret[i];
				}
			}

			return ret;
		}

		throw new IOException("Malformed url_list");
	}
}
