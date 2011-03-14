package org.flex.test.rest.login;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {

	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAILED = "failed";

	private String status;

	public Result() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
