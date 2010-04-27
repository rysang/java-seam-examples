package org.gmoss.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void copy(InputStream src, OutputStream dest, int count)
			throws IOException {

		byte[] b = new byte[2048];
		int bRead = 0;
		int total = 0;

		while (total != count) {
			bRead = count - total;
			bRead = src.read(b, 0, (bRead > b.length) ? b.length : bRead);
			dest.write(b, 0, bRead);
			total += bRead;
		}

		dest.flush();
	}
}
