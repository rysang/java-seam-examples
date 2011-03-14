package org.flex.test.rest.login;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FailedResult extends Result {
	private String message;

	public FailedResult() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
