package org.nuts.bolts.scheduling.task;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

/**
 * This is the task class to be passed to the scheduler
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
public class Task implements Comparable<Task> {
    /**
     * Unique id
     */
    private UUID                     uuid;
    private TaskType                 taskType;
    private IOType                   ioType;

    /**
     * Where to execute this task.
     */
    private ExecutionEnvironmentType executionEnvironmentType;

    /**
     * Parameters to be sent
     */
    private Serializable[]           parameters;

    /**
     * Name of the bean to be set as runnable.
     */
    private String                   name;

    public Task() {
        uuid = UUID.randomUUID();
    }

    public Task(TaskType taskType, IOType ioType, Serializable... parameters) {
        super();
        this.taskType = taskType;
        this.ioType = ioType;
        this.parameters = parameters;
    }

    public UUID getUuid() {
        return uuid;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public IOType getIoType() {
        return ioType;
    }

    public void setIoType(IOType ioType) {
        this.ioType = ioType;
    }

    public Serializable[] getParameters() {
        return parameters;
    }

    public void setParameters(Serializable... parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExecutionEnvironmentType getExecutionEnvironmentType() {
        return executionEnvironmentType;
    }

    public void setExecutionEnvironmentType(ExecutionEnvironmentType executionEnvironmentType) {
        this.executionEnvironmentType = executionEnvironmentType;
    }

    @Override
    public int compareTo(Task o) {
        return uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        return "Task [uuid=" + uuid + ", taskType=" + taskType + ", ioType=" + ioType + ", parameters="
                + Arrays.toString(parameters) + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        }
        else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

}
