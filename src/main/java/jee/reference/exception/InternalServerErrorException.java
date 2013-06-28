package jee.reference.exception;

public class InternalServerErrorException extends Exception {
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String errorMessage) {
        super(errorMessage);
    }

    public InternalServerErrorException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
