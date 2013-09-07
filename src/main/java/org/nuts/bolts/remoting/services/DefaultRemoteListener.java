package org.nuts.bolts.remoting.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.nuts.bolts.remoting.services.api.ResultListener;

public class DefaultRemoteListener extends UnicastRemoteObject implements
		ResultListener {
	private static final long serialVersionUID = 1L;

	public DefaultRemoteListener() throws RemoteException {
		super();
	}

	@Override
	public void onError(Throwable result) {
	}

	@Override
	public void onResult(Object result) {
		System.out.println(result);
	};
}
