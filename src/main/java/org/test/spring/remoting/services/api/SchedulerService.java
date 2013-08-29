package org.test.spring.remoting.services.api;

import java.rmi.RemoteException;

public interface SchedulerService {

	public long getRunningThreads() throws RemoteException;

	public long getTotalThreads() throws RemoteException;

	public long getMaximumRunningThreads() throws RemoteException;

	public void runTask(TaskType type, ResultListener listener,
			Object... params) throws RemoteException;

}
