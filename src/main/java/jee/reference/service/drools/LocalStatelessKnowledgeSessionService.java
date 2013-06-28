package jee.reference.service.drools;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jee.reference.exception.InternalServerErrorException;
import jee.reference.util.Logged;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.slf4j.Logger;

@Logged
@ApplicationScoped
public class LocalStatelessKnowledgeSessionService extends StatelessKnowledgeSessionService {
    @Inject
    private Logger logger;
    @Inject
    private DroolsStaticService droolsStaticService;

    @PostConstruct
    public void init() {
        KnowledgeBuilder knowledgeBuilder = droolsStaticService.createNewKnowledgeBuilder();
        Resource rule = droolsStaticService.createRuleResource("drools/filterDeletedPeople.drl");
        knowledgeBuilder.add(rule, ResourceType.DRL);

        if (knowledgeBuilder.hasErrors()) {
            for (KnowledgeBuilderError knowledgeBuilderError : knowledgeBuilder.getErrors()) {
                logger.error(knowledgeBuilderError.getMessage());
            }
            throw new IllegalStateException("Couldn't build Drools knowledgebase!");
        }

        KnowledgeBase knowledgeBase = knowledgeBuilder.newKnowledgeBase();
        setStatelessKnowledgeSession(knowledgeBase.newStatelessKnowledgeSession());
        setLoggerForStatelessKnowledgeSession(droolsStaticService.registerConsoleLogger(getStatelessKnowledgeSession()));
    }

    @PreDestroy
    public void preDestroy() {
        endService();
    }

    @Override
    public void executeInStatelessKnowledgeSession(List<Object> factList) throws InternalServerErrorException {
        getStatelessKnowledgeSession().execute(factList);
    }
}
