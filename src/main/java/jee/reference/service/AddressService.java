package jee.reference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import jee.reference.model.Address;
import jee.reference.model.Address_;
import jee.reference.model.Passport;
import jee.reference.model.Passport_;
import jee.reference.model.Person;
import jee.reference.model.Person_;
import jee.reference.util.Admin;
import jee.reference.util.Logged;

@Logged
@Stateless
public class AddressService {
    @Inject
    @Admin
    private EntityManager entityManager;

    public List<Address> getOwnersAddresses(String passportIssuingCountry) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> addressQuery = criteriaBuilder.createQuery(Address.class);
        addressQuery.distinct(true);

        Root<Address> addressRoot = addressQuery.from(Address.class);

        Join<Person, Passport> join = addressRoot.join(Address_.residents).join(Person_.passport);

        addressQuery.select(addressRoot).where(criteriaBuilder.equal(join.get(Passport_.issuingCountry), passportIssuingCountry));
        addressRoot.fetch(Address_.residents);

        List<Address> addresses = entityManager.createQuery(addressQuery).getResultList();

        return addresses;
    }
}
