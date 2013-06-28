package jee.reference.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jee.reference.exception.RecordNotFoundException;

import org.slf4j.Logger;

@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {
    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Exception exception) {
        Response result;

        logger.warn("REST error!", exception);

        try {
            throw exception;
        } catch (RecordNotFoundException e) {
            String errorMessage = enhanceErrorMessage("Record not found!", e);
            result = handleException(e, HttpServletResponse.SC_NOT_FOUND, errorMessage);
        } catch (ClassNotFoundException e) {
            result = handleException(e, HttpServletResponse.SC_NOT_FOUND, MediaType.TEXT_PLAIN_TYPE);
        } catch (Exception e) {
            result = handleException(e, HttpServletResponse.SC_BAD_REQUEST);
        }

        return result;
    }

    private String enhanceErrorMessage(String errorPrefix, Exception e) {
        return errorPrefix + "\n" + e.getMessage();
    }

    private Response handleException(Exception e, int statusCode) {
        String errorMessage = e.getMessage();
        return handleException(e, statusCode, errorMessage);
    }

    private Response handleException(Exception e, int statusCode, MediaType mediaType) {
        String errorMessage = e.getMessage();
        return handleException(e, statusCode, errorMessage, mediaType);
    }

    private Response handleException(Exception e, int statusCode, String errorMessage) {
        return handleException(e, statusCode, errorMessage, MediaType.TEXT_PLAIN_TYPE);
    }

    private Response handleException(Exception e, int statusCode, String errorMessage, MediaType mediaType) {
        Response result;

        result = Response.status(statusCode).entity(errorMessage).type(mediaType).build();

        return result;
    }

}
