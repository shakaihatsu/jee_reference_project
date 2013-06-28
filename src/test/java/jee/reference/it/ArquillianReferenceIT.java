package jee.reference.it;

import java.io.File;

import jee.reference.TopLevelPackageMarker;
import jee.reference.meta.JBOSS_AS7;
import jee.reference.meta.POI;
import jee.reference.meta.POITag;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianReferenceIT {
    @JBOSS_AS7
    @POI(
        tags = { POITag.BE_CAREFUL },
        value = "Arquillian needs one and only one container adapter on the classpath. In case of a container managed JBoss AS, the maven dependency version must match the version of the JBoss installation.")
    public static final String ARQUILLIAN_CONTAINER_ADAPTER_MAVEN_DEPENDENCY = "jboss-as-arquillian-container-managed";

    private static final String MAVEN_PROFILE = null;

    @Deployment
    public static final Archive<?> createDeployment() {
        File[] libs;

        if (MAVEN_PROFILE == null) {
            libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies().asFile();
        } else {
            libs = Maven.resolver().loadPomFromFile("pom.xml", MAVEN_PROFILE).importRuntimeAndTestDependencies().asFile();
        }

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs).addPackages(true, TopLevelPackageMarker.class.getPackage())
            .addAsWebInfResource("test-ds.xml").addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsResource("extensions", "META-INF/services/javax.enterprise.inject.spi.Extension").addAsWebInfResource("test-beans.xml", "beans.xml");

        // archive.as(ZipExporter.class).exportTo(new File("e:/tmp/test.war"), true);

        addInitSqlAssets(archive);

        return archive;
    }

    @Test
    public void test() throws Exception {
        Assert.assertTrue(true);
    }

    private static void addInitSqlAssets(WebArchive archive) {
        archive.addAsResource("META-INF/sql/initPersonTable.sql", "META-INF/sql/initPersonTable.sql");
    }
}
