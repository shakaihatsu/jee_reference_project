package jee.reference.ext.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Pseudo-veto to not produce default in CDI context
@Spring
@Component
public class ConfigProperties {
    @Value("${project.version}")
    private String projectVersion;
    @Value("${project.buildNumber}")
    private String buildNumber;
    @Value("${project.timestamp}")
    private Long timestamp;

    @Value("${db.health.check.statement}")
    private String dbHealthCheckerStatement;

    @Value("${rest.recursive.host}")
    private String host;
    @Value("${rest.recursive.port}")
    private Long port;
    @Value("${rest.recursive.path}")
    private String path;

    @Value("${drools.resource.scanner.interval}")
    private Long droolsResourceScannerInterval;

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDbHealthCheckerStatement() {
        return dbHealthCheckerStatement;
    }

    public void setDbHealthCheckerStatement(String dbHealthCheckerStatement) {
        this.dbHealthCheckerStatement = dbHealthCheckerStatement;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getDroolsResourceScannerInterval() {
        return droolsResourceScannerInterval;
    }

    public void setDroolsResourceScannerInterval(Long droolsResourceScannerInterval) {
        this.droolsResourceScannerInterval = droolsResourceScannerInterval;
    }
}
