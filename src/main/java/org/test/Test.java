package org.test;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Scope(ScopeType.CONVERSATION)
@Name("test")
public class Test {

	@Logger
	private Log log;
	private Date date;

	public Test() {

	}

	@Create
	public void create() {
		log.info(" Bean Created.");
	}

	@Destroy
	public void destroy() {
		log.info(" Bean Destroyed.");
	}

	public String getDateString() {
		log.info(" DateString called.");
		return new Date().toString();
	}

	@Begin(nested = true)
	public void begin() {
		log.info(" Begin called.");
	}

	@End
	public void end() {
		log.info(" End called.");
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		log.info("Date selected: " + date);
		this.date = date;
	}
}