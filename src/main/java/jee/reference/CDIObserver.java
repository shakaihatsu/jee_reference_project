package jee.reference;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class CDIObserver implements Extension {
    public void observeEvent(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
        System.out.println("BeforeBeanDiscovery: " + event.toString());
    }
}
