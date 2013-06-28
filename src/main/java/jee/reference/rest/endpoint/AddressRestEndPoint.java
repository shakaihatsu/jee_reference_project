package jee.reference.rest.endpoint;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.model.Address;
import jee.reference.service.AddressService;
import jee.reference.util.Logged;

@Logged
@Path("/address")
@RequestScoped
public class AddressRestEndPoint {
    @Inject
    private AddressService addressService;

    @GET
    @Path("/{passportIssuingCountry}")
    @Produces("application/json")
    public List<Address> getOwnersAddresses(@PathParam("passportIssuingCountry") String passportIssuingCountry) throws RecordNotFoundException {
        return addressService.getOwnersAddresses(passportIssuingCountry);
    }

}
