package org.price.manga.reader.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class Utils {

	public static byte[] createImage(String link) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(link)
				.openConnection();
		connection.setDoInput(true);

		try (InputStream is = connection.getInputStream()) {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(is, bos);

			return bos.toByteArray();
		}

	}

	public static String getBaseUrl(String page) throws URISyntaxException {
		final URI uri = new URI(page);

		String link = uri.getScheme() + "://" + uri.getHost() + ':'
				+ (uri.getPort() == -1 ? 80 : uri.getPort());

		return link;
	}
}
