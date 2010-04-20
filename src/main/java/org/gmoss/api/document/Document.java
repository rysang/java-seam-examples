package org.gmoss.api.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public interface Document {
	public boolean isFolder();

	public boolean isReadOnly();

	public InputStream getInputStream() throws IOException;

	public OutputStream getOutputStream() throws IOException;

	public String getPath();

	public String getName();

	public Date getLastModified();

	public void setProperty(String name, String value);

	public String getProperty(String name);
}
