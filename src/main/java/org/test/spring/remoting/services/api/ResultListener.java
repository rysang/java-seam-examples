package org.test.spring.remoting.services.api;

public interface ResultListener {
	public void onResult(Object result);

	public void onError(Throwable result);
}
