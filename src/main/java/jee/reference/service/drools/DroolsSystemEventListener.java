package jee.reference.service.drools;

import org.drools.SystemEventListener;

public class DroolsSystemEventListener implements SystemEventListener {

    private String lastExceptionMessage;
    private Throwable lastException;

    @Override
    public void info(String message) {
    }

    @Override
    public void info(String message, Object object) {
    }

    @Override
    public void warning(String message) {
    }

    @Override
    public void warning(String message, Object object) {
    }

    @Override
    public void exception(String message, Throwable e) {
        setLastException(e);
        setLastExceptionMessage(message);
    }

    @Override
    public void exception(Throwable e) {
        setLastException(e);
        setLastExceptionMessage("N/A");
    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void debug(String message, Object object) {
    }

    synchronized public Throwable getLastException() {
        return lastException;
    }

    synchronized public void setLastException(Throwable lastException) {
        this.lastException = lastException;
    }

    synchronized public String getLastExceptionMessage() {
        return lastExceptionMessage;
    }

    synchronized public void setLastExceptionMessage(String lastExceptionMessage) {
        this.lastExceptionMessage = lastExceptionMessage;
    }

    public boolean hasException() {
        return getLastException() != null;
    }
}
