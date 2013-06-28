package jee.reference.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import jee.reference.util.Logged;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Logged
@Interceptor
public class LoggingInterceptor {
    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        String className = invocationContext.getTarget().getClass().getName();

        String methodName = invocationContext.getMethod().getName();
        if (className.indexOf("$") > 0) {
            className = className.substring(0, className.indexOf("$"));
        }

        Logger logger = LoggerFactory.getLogger(className);

        if (isBooleanMethod(invocationContext)) {
            return logBooleanMethod(invocationContext, methodName, logger);
        } else {
            return logNormalMethod(invocationContext, methodName, logger);
        }
    }

    private Object logNormalMethod(InvocationContext invocationContext, String methodName, Logger logger) throws Exception {
        long startTime = System.currentTimeMillis();

        logger.debug(methodName + " STARTED");
        try {
            Object retValue = invocationContext.proceed();
            logger.debug(methodName + " FINISHED in " + (System.currentTimeMillis() - startTime) + " ms");
            return retValue;
        } catch (Exception e) {
            logger.warn(methodName + " FAILED in " + (System.currentTimeMillis() - startTime) + " ms");
            throw e;
        }
    }

    private Object logBooleanMethod(InvocationContext invocationContext, String methodName, Logger logger) throws Exception {
        try {
            Object retValue = invocationContext.proceed();
            logger.debug(methodName + " " + retValue.toString().toUpperCase());
            Object[] objs = invocationContext.getParameters();
            if (objs != null) {
                int i = 1;
                for (Object o : objs) {
                    String message = i++ + ": " + methodName + " " + (o != null ? o.toString() : "null");
                    logger.debug(message);
                }
            }
            return retValue;
        } catch (Exception e) {
            logger.debug(methodName + "FAILED");
            throw e;
        }
    }

    private boolean isBooleanMethod(InvocationContext invocationContext) {
        return Boolean.class.equals(invocationContext.getMethod().getReturnType()) || boolean.class.equals(invocationContext.getMethod().getReturnType());
    }
}
