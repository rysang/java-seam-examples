package org.nuts.bolts.scheduling.policy;

import org.nuts.bolts.scheduling.task.IOType;
import org.nuts.bolts.scheduling.task.TaskType;

public class SchedulePolicy {
    private TaskType taskType;
    private IOType   ioType;

    public SchedulePolicy() {

    }

    public SchedulePolicy(TaskType taskType, IOType ioType) {
        super();
        this.taskType = taskType;
        this.ioType = ioType;
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

    @Override
    public String toString() {
        return "Policy [taskType=" + taskType + ", ioType=" + ioType + "]";
    }

}
