package org.nuts.bolts.remoting.services.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResultListener extends Remote {
	public void onResult(Object result) throws RemoteException;

	public void onError(Throwable result) throws RemoteException;
}
