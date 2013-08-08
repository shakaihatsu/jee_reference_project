package jee.reference.external.spring;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.seam.spring.context.Configuration;
import org.jboss.seam.spring.context.SpringContext;
import org.jboss.seam.spring.inject.SpringBean;
import org.springframework.context.ApplicationContext;

@ApplicationScoped
public class SpringConfigurer {
    @Produces
    @ApplicationScoped
    @SpringContext
    @Configuration(locations = { "classpath*:META-INF/spring/springContext.xml" })
    @SuppressWarnings("unused")
    private ApplicationContext springContext;

    @Produces
    @SpringBean
    @SuppressWarnings("unused")
    private ConfigProperties configProperties;
}
