package org.test.spring.remoting.services.api;

import java.util.concurrent.ExecutorService;

public interface SchedulerService extends ExecutorService {

	public long getRunningThreads();

	public long getTotalThreads();

	public long getMaximumRunningThreads();

}
