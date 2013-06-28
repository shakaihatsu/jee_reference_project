package jee.reference.service.drools;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;

import jee.reference.util.Logged;

import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.io.Resource;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatelessKnowledgeSession;

@Logged
@ApplicationScoped
public class DroolsStaticService {
    public KnowledgeBuilder createNewKnowledgeBuilder() {
        return KnowledgeBuilderFactory.newKnowledgeBuilder();
    }

    public Resource createRuleResource(String droolClassPathUrl) {
        return ResourceFactory.newClassPathResource(droolClassPathUrl);
    }

    public KnowledgeRuntimeLogger registerConsoleLogger(StatelessKnowledgeSession statelessKnowledgeSession) {
        return KnowledgeRuntimeLoggerFactory.newConsoleLogger(statelessKnowledgeSession);
    }

    public void startResourceChangeObserverServices() {
        ResourceFactory.getResourceChangeNotifierService().start();
        ResourceFactory.getResourceChangeScannerService().start();
    }

    public ResourceChangeScannerConfiguration getNewResourceChangeScannerConfiguration() {
        return ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
    }

    public void configureResourceChangeScannerService(ResourceChangeScannerConfiguration resourceChangeScannerConfiguration) {
        ResourceFactory.getResourceChangeScannerService().configure(resourceChangeScannerConfiguration);
    }

    public KnowledgeAgent getNewKnowledgeAgent(String knowledgeAgentName) {
        return KnowledgeAgentFactory.newKnowledgeAgent(knowledgeAgentName);
    }

    public Resource buildChangeSet(String changeSetXml) {
        return ResourceFactory.newReaderResource(new StringReader(changeSetXml));
    }

    public DroolsSystemEventListener createNewDroolsSystemEventListener() {
        return new DroolsSystemEventListener();
    }
}
