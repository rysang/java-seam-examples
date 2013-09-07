package org.nuts.bolts.scheduling.policy.exceptions;

import org.nuts.bolts.scheduling.exceptions.ScheduleException;

public class PolicyException extends ScheduleException {

    public PolicyException() {
        super();
    }

    public PolicyException(String message, Throwable cause) {
        super(message, cause);

    }

    public PolicyException(String message) {
        super(message);

    }

    public PolicyException(Throwable cause) {
        super(cause);

    }

    @Override
    public String toString() {
        return "PolicyException [" + super.toString() + "]";
    }

}
