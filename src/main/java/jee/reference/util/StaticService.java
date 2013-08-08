package jee.reference.util;

import javax.enterprise.context.ApplicationScoped;

import jee.reference.external.drools.fact.Person;

@Logged
@ApplicationScoped
public class StaticService {
    public Person createNewPersonFact() {
        return new Person();
    }
}
