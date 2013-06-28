package jee.reference;

import java.util.Collections;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import jee.reference.model.Address;
import jee.reference.model.EStreetType;
import jee.reference.model.Passport;
import jee.reference.model.Person;
import jee.reference.util.Admin;
import jee.reference.util.Logged;

@Logged
@Startup
@Singleton
public class DatabaseInitializer {
    @Inject
    @Admin
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        Person person = new Person();
        person.setFirstName("Jane");
        person.setLastName("Dow");
        person.setDateOfBirth(new Date());

        Passport passport = new Passport();
        passport.setDocumentId("DocumentID");
        passport.setIssuingCountry("SomeCountry");
        passport.setOwner(person);
        person.setPassport(passport);

        Address address = new Address();
        person.setAddresses(Collections.singleton(address));
        address.setStreetName("StreetName");
        address.setStreetType(EStreetType.AVENUE);
        address.setZipOrPostalCode("PostalCode");

        entityManager.persist(person);

    }
}
