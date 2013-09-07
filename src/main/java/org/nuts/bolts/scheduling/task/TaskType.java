package org.nuts.bolts.scheduling.task;

/**
 * This is the type of the tasks that can be ran.
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
public enum TaskType {
    /**
     * This type of task is an utility task usually meant for maintenance of the scheduler or it's components. Running
     * this task should be a priority for the system and these kind of tasks should be ran in a special pool.
     */
    MAINTENANCE_TASK,

    /**
     * This type is a short running type of task. These kind of tasks should return quick results and should not take
     * more than 10 -30 secons.
     */
    SHORT_RUNNING_TASK,

    /**
     * This type is a long running type of task. These tasks take a lot of time ex: scanning for ip(s).
     */
    LONG_RUNNING_TASK
}
