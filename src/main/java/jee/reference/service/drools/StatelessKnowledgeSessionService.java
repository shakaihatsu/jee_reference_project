package jee.reference.service.drools;

import java.util.List;

import jee.reference.exception.InternalServerErrorException;

import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.runtime.StatelessKnowledgeSession;

public abstract class StatelessKnowledgeSessionService {
    private StatelessKnowledgeSession statelessKnowledgeSession;
    private KnowledgeRuntimeLogger loggerForStatelessKnowledgeSession;

    public abstract void executeInStatelessKnowledgeSession(List<Object> factList) throws InternalServerErrorException;

    public void endService() {
        closeLoggers();
        statelessKnowledgeSession = null;
    }

    protected void closeLoggers() {
        if (loggerForStatelessKnowledgeSession != null) {
            loggerForStatelessKnowledgeSession.close();
            loggerForStatelessKnowledgeSession = null;
        }
    }

    protected StatelessKnowledgeSession getStatelessKnowledgeSession() {
        return statelessKnowledgeSession;
    }

    protected void setStatelessKnowledgeSession(StatelessKnowledgeSession statelessKnowledgeSession) {
        this.statelessKnowledgeSession = statelessKnowledgeSession;
    }

    protected KnowledgeRuntimeLogger getLoggerForStatelessKnowledgeSession() {
        return loggerForStatelessKnowledgeSession;
    }

    protected void setLoggerForStatelessKnowledgeSession(KnowledgeRuntimeLogger loggerForStatelessKnowledgeSession) {
        this.loggerForStatelessKnowledgeSession = loggerForStatelessKnowledgeSession;
    }
}
