package jee.reference.facade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jee.reference.model.Passport;
import jee.reference.service.dao.PassportService;
import jee.reference.util.Logged;

@Logged
@ApplicationScoped
public class PassportFacade {
    @Inject
    private PassportService passportService;

    public Passport updatePassport(Long passportId, Passport passport) {
        return passportService.updatePassport(passportId, passport);
    }
}
