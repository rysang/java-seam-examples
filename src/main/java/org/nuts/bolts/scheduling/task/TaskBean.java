package org.nuts.bolts.scheduling.task;

import java.io.Serializable;

public interface TaskBean<T> extends Runnable {
    public T call(Serializable... parameters);
}
