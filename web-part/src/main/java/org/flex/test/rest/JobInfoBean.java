package org.flex.test.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobInfoBean {
	public String name;
	public String status;
	public int pages;

	// just to make JAXB happy
	public JobInfoBean() {
	};

	public JobInfoBean(String name, String status, int pages) {
		this.name = name;
		this.status = status;
		this.pages = pages;
	}

}
