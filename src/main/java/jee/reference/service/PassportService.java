package jee.reference.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import jee.reference.meta.POI;
import jee.reference.model.Passport;
import jee.reference.model.Person;
import jee.reference.util.Admin;
import jee.reference.util.Logged;

@Logged
@Stateless
public class PassportService {
    @Inject
    @Admin
    private EntityManager entityManager;

    @POI("Explicit optimistic lock")
    public Passport updatePassport(Long passportId, Passport passport) {
        Passport unupdatedPassport = entityManager.find(Passport.class, passportId);

        issueOptimisiticLockOnOwner(unupdatedPassport);

        passport.setId(passportId);
        Passport updatedPassport = entityManager.merge(passport);

        return updatedPassport;
    }

    private void issueOptimisiticLockOnOwner(Passport unupdatedPassport) {
        Person owner = unupdatedPassport.getOwner();
        entityManager.lock(owner, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }
}
