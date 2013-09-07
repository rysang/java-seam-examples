package org.nuts.bolts.scheduling;

import org.nuts.bolts.scheduling.policy.PolicyManager;
import org.nuts.bolts.scheduling.policy.exceptions.PolicyNotAccepted;
import org.nuts.bolts.scheduling.task.Task;

/**
 * Scheduler node.
 * <p>
 * TODO
 * </p>
 * 
 * <p>
 * Copyright (C) 2013 Ceragon Networks. All rights reserved.
 * </p>
 * 
 * @author cristianp
 */
public interface SchedulerNode {
    public PolicyManager getPolicyManager();

    public String getName();

    public void ping();

    public void schedule(Task task) throws PolicyNotAccepted;

    public Long getMaximumTaskCount();

    public Long getRunningTaskCount();
}
