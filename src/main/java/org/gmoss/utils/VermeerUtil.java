package org.gmoss.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class VermeerUtil {

	public static final String CONTENT_TYPE = "x-vermeer-content-type";

	public static final String CONTENT_TYPE_APP = "application/x-vermeer-urlencoded";

	public static Map<String, Object> parseRequest(HttpServletRequest req)
			throws IOException {

		int b = -1;
		StringBuffer sb = new StringBuffer();

		InputStream is = null;
		int len = req.getContentLength();

		if (len != -1) {

			is = req.getInputStream();
			while ((b = is.read()) != -1) {
				sb.append((char) b);
				if (b == '\n')
					break;
			}
		}

		Map<String, Object> ret = getParams(sb.toString());
		ret.put("inputStream", is);
		ret.put("lengthLeft", len - sb.length());

		return ret;

	}

	private final static Map<String, Object> getParams(String str)
			throws IOException {

		HashMap<String, Object> h = new HashMap<String, Object>();
		final String[] pss = str.split("&");
		int idx = 0;

		for (String s : pss) {
			idx = s.indexOf('=');
			if (idx != -1) {
				h
						.put(URLDecoder.decode(s.substring(0, idx), "UTF-8"),
								URLDecoder
										.decode(s.substring(idx + 1), "UTF-8")
										.trim());
			}
		}

		return h;
	}

}
