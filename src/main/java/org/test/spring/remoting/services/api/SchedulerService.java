package org.test.spring.remoting.services.api;

import java.util.UUID;

public interface SchedulerService {

	public long getRunningThreads();

	public long getTotalThreads();

	public long getMaximumRunningThreads();

	public void runTask(TaskType type, UUID taskId, Object... params);

}
