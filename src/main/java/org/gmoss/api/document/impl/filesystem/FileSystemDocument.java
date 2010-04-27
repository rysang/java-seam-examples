package org.gmoss.api.document.impl.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.gmoss.api.document.Document;

public class FileSystemDocument implements Document {

	private File file;

	public FileSystemDocument() {

	}

	public FileSystemDocument(File file) {
		setFile(file);
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(getFile());
	}

	public Date getLastModified() {
		return new Date(getFile().lastModified());
	}

	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(getFile(), false);
	}

	public String getPath() {
		if (getParent() == null) {
			return "/";
		}

		StringBuilder sb = new StringBuilder(100);
		String parentPath = getParent().getPath();
		sb.append(parentPath.equals("/") ? "" : parentPath);
		sb.append('/');
		sb.append(getName());

		return sb.toString();
	}

	public boolean isFolder() {
		return getFile().isDirectory();
	}

	public boolean isReadOnly() {
		return false;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public FileSystemDocument getParent() {
		FileSystemDocument parent = new FileSystemDocument();
		parent.setFile(getFile().getParentFile());
		return parent;
	}

	public String getName() {
		return getFile().getName();
	}

	public String getProperty(String name) {
		if ("uid".equals(name)) {
			return UUID.randomUUID().toString();
		}
		return null;
	}

	public void setProperty(String name, String value) {

	}

	@Override
	public String toString() {
		return "File: " + getPath();
	}

}
