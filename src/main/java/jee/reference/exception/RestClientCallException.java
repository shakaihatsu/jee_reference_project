package jee.reference.exception;

public class RestClientCallException extends Exception {
    private static final long serialVersionUID = 1L;

    public RestClientCallException(String errorMessage) {
        super(errorMessage);
    }

    public RestClientCallException(String message, Throwable cause) {
        super(message, cause);
    }

}