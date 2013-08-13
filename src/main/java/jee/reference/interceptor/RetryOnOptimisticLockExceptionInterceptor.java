package jee.reference.interceptor;

import javax.ejb.EJBException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.OptimisticLockException;

import jee.reference.exception.OptimisticLockRetryException;
import jee.reference.meta.POI;
import jee.reference.meta.POITag;
import jee.reference.meta.TODO;
import jee.reference.meta.TODOTag;
import jee.reference.service.dao.RetryOnOptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TODO(tags = { TODOTag.MAY_CHANGE_IN_THE_FUTURE, TODOTag.INTERCEPTORS_1_1 },
    value = "Probably should use @Priority from Interceptors 1.1, remove from beans.xml")
@RetryOnOptimisticLockException
@Interceptor
public class RetryOnOptimisticLockExceptionInterceptor {

    @AroundInvoke
    public Object retry(InvocationContext context) throws Throwable {
        RetryOnOptimisticLockException retryOnOptimisticLockExceptionAnnotation = getAnnotation(context);

        String className = context.getTarget().getClass().getName();

        int junkStartIndex = className.indexOf("$");
        if (junkStartIndex > 0) {
            className = className.substring(0, junkStartIndex);
        }

        String methodName = context.getMethod().getName();

        Logger logger = LoggerFactory.getLogger(className);

        Object outcome = null;

        long startTime = System.currentTimeMillis();
        boolean succesful = false;
        while (!succesful && System.currentTimeMillis() - startTime < retryOnOptimisticLockExceptionAnnotation.retryDurationInMilliseconds()) {
            try {
                outcome = context.proceed();

                succesful = true;
            } catch (Exception e) {
                try {
                    processException(e);
                } catch (OptimisticLockException e1) {
                    logger.warn("Concurrent modification during " + methodName + ", retrying...");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e2) {
                        logger.warn("Thread interrupted while sleeping before retrying " + methodName + ".", e2);
                    }
                }
            }
        }

        if (!succesful) {
            String errorMessage = methodName + " failed.";
            logger.warn(errorMessage);
            throw new OptimisticLockRetryException(errorMessage);
        }

        return outcome;
    }

    private RetryOnOptimisticLockException getAnnotation(InvocationContext ctx) {
        RetryOnOptimisticLockException loggedAnnotation = ctx.getMethod().getAnnotation(RetryOnOptimisticLockException.class);

        if (loggedAnnotation == null) {
            loggedAnnotation = ctx.getTarget().getClass().getAnnotation(RetryOnOptimisticLockException.class);
        }

        if (loggedAnnotation == null) {
            throw new IllegalStateException();
        }

        return loggedAnnotation;
    }

    @POI(tags = { POITag.STRANGE_BEHAVIOUR }, value = "Not sure why we got once the other exception stack on our project.")
    private void processException(Exception e) throws OptimisticLockException, Exception {
        if (e != null && e instanceof EJBException) {
            Throwable nested = e.getCause();
            if (nested != null && OptimisticLockException.class.equals(nested.getClass())) {
                throw (OptimisticLockException) nested;
            }
        }
        // if (e != null && EJBTransactionRolledbackException.class.equals(e.getClass())) {
        // Throwable nested = e.getCause();
        // if (nested != null && RollbackException.class.equals(nested.getClass())) {
        // nested = nested.getCause();
        // if (nested != null && OptimisticLockException.class.equals(nested.getClass())) {
        // throw (OptimisticLockException) nested;
        // }
        // }
        // }
        throw e;
    }
}
