package org.gmoss.utils;

public class Path {
	private String webUrl;
	private String fileUrl;

	public Path() {

	}

	public Path(String webUrl, String fileUrl) {
		setFileUrl(fileUrl);
		setWebUrl(webUrl);
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}
}
