package jee.reference.exception;

public class RecordNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
