package org.test.spring.remoting.services;

import java.rmi.RemoteException;

import org.test.spring.remoting.services.api.ResultListener;
import org.test.spring.remoting.services.api.SchedulerService;
import org.test.spring.remoting.services.api.TaskType;

public class SchedulerServiceImpl implements SchedulerService {

	public SchedulerServiceImpl() {

	}

	@Override
	public long getRunningThreads() throws RemoteException {
		return 0;
	}

	@Override
	public long getTotalThreads() throws RemoteException {

		return 0;
	}

	@Override
	public long getMaximumRunningThreads() throws RemoteException {

		return 0;
	}

	@Override
	public void runTask(TaskType type, ResultListener listener,
			Object... params) throws RemoteException {

		if (listener != null) {
			listener.onResult("Hey man !!!!");
		}
	}
}
