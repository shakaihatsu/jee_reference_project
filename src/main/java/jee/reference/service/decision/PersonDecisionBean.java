package jee.reference.service.decision;

import javax.enterprise.context.ApplicationScoped;

import jee.reference.model.Person;
import jee.reference.util.Logged;

@Logged
@ApplicationScoped
public class PersonDecisionBean {
    public boolean personDoesntExist(Person person) {
        return person == null;
    }
}
