package jee.reference.exception;

public class OptimisticLockRetryException extends Exception {
    private static final long serialVersionUID = 1L;

    public OptimisticLockRetryException(String errorMessage) {
        super(errorMessage);
    }
}