package org.nuts.bolts.scheduling.policy.exceptions;

public class PolicyNotAccepted extends PolicyException {

    public PolicyNotAccepted() {
        super();
    }

    public PolicyNotAccepted(String message, Throwable cause) {
        super(message, cause);
    }

    public PolicyNotAccepted(String message) {
        super(message);
    }

    public PolicyNotAccepted(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "PolicyNotAccepted [" + super.toString() + "]";
    }

}
