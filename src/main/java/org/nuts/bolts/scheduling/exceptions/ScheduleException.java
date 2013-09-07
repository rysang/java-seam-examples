package org.nuts.bolts.scheduling.exceptions;

public class ScheduleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ScheduleException() {

    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "ScheduleException [" + super.toString() + "]";
    }
}
