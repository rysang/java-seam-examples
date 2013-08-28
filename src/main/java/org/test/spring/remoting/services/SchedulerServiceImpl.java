package org.test.spring.remoting.services;

import java.util.UUID;

import org.test.spring.remoting.services.api.SchedulerService;
import org.test.spring.remoting.services.api.TaskType;

public class SchedulerServiceImpl implements SchedulerService {

	public SchedulerServiceImpl() {

	}

	@Override
	public long getRunningThreads() {
		return 0;
	}

	@Override
	public long getTotalThreads() {

		return 0;
	}

	@Override
	public long getMaximumRunningThreads() {

		return 0;
	}

	@Override
	public void runTask(TaskType type, UUID taskId, Object... params) {

	}
}
