package org.gmoss.api.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GMOSSService {

	public Version getVersion();

	public void setVersion(Version version);

	public void handleService(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException;
}
