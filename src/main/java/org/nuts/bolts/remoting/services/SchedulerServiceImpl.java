package org.nuts.bolts.remoting.services;

import java.rmi.RemoteException;

import org.nuts.bolts.remoting.services.api.ResultListener;
import org.nuts.bolts.remoting.services.api.SchedulerService;
import org.nuts.bolts.remoting.services.api.TaskType;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SchedulerServiceImpl implements SchedulerService, ApplicationListener {

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
    public void runTask(TaskType type, ResultListener listener, Object... params) throws RemoteException {

        if (listener != null) {
            listener.onResult("Hey man !!!!");
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("EVENT: " + event);
    }
}
