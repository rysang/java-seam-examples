package org.gmoss.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gmoss.api.service.GMOSSService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainFilter implements Filter {

	private static final long serialVersionUID = 731774720777724488L;
	private ApplicationContext context;
	private Logger LOG = Logger.getLogger(MainFilter.class);

	public void destroy() {
		context = null;

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		GMOSSService service = null;
		try {

			service = (GMOSSService) context.getBean(((HttpServletRequest) req)
					.getMethod());
		} catch (Exception e) {
			LOG.error("Error resolving service.", e);
			chain.doFilter(req, resp);
			return;
		}

		service.handleService((HttpServletRequest) req,
				(HttpServletResponse) resp);
	}

	public void init(FilterConfig config) throws ServletException {
		context = new ClassPathXmlApplicationContext(
				"org/gmoss/applicationContext.xml");
	}
}
