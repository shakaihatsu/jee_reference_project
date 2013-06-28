package jee.reference.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Resources {
    @Produces
    @Admin
    @PersistenceContext(unitName = "admin")
    @SuppressWarnings("unused")
    private EntityManager entityManager;

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Produces
    public ModelMapper produceModelMapper() {
        return new ModelMapper();
    }
}
