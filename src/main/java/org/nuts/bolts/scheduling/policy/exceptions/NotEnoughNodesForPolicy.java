package org.nuts.bolts.scheduling.policy.exceptions;

public class NotEnoughNodesForPolicy extends PolicyException {
    private static final long serialVersionUID = 1L;

    public NotEnoughNodesForPolicy() {

    }

    public NotEnoughNodesForPolicy(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughNodesForPolicy(String message) {
        super(message);
    }

    public NotEnoughNodesForPolicy(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "NotEnoughNodesForPolicy [" + super.toString() + "]";
    }

}
