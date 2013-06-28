package jee.reference.rest.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jee.reference.facade.PassportFacade;
import jee.reference.model.Passport;
import jee.reference.util.Logged;

@Logged
@Path("/passport")
@RequestScoped
public class PassportRestEndPoint {
    @Inject
    private PassportFacade passportFacade;

    @PUT
    @Path("/{passportId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Passport updatePassport(@PathParam("passportId") Long passportId, Passport passport) {
        return passportFacade.updatePassport(passportId, passport);
    }
}
