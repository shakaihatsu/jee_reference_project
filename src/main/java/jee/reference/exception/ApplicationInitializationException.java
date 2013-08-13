package jee.reference.exception;

public class ApplicationInitializationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ApplicationInitializationException(String errorMessage) {
        super(errorMessage);
    }

    public ApplicationInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
