package org.test;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Scope(ScopeType.SESSION)
@Name("test")
public class Test implements Serializable {
	private static final long serialVersionUID = -2112893929515041544L;

	@Logger
	private transient Log log;

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

}