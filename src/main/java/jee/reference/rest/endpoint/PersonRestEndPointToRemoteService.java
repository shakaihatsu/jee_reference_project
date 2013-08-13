package jee.reference.rest.endpoint;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.meta.QND;
import jee.reference.model.Person;
import jee.reference.model.dto.PersonDto;
import jee.reference.service.api.PersonService;
import jee.reference.service.remoting.RMI;
import jee.reference.util.Logged;

@Logged
@Path("/personViaRemote")
@RequestScoped
public class PersonRestEndPointToRemoteService {
    @Inject
    @RMI
    private PersonService personService;

    @GET
    @Produces("application/json")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GET
    @Path("/{personId}")
    @Produces("application/json")
    public Person getPerson(@PathParam("personId") Long personId) throws RecordNotFoundException {
        return personService.getPerson(personId);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Long createPerson(Person person) {
        return personService.createPerson(person);
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Person updatePerson(Person person) {
        return personService.updatePerson(person);
    }

    @GET
    @Path("/data")
    @Produces("application/json")
    public List<PersonDto> getAllPersonData() throws RecordNotFoundException {
        return personService.getAllPersonData();
    }

    @GET
    @Path("/data/{personId}")
    @Produces("application/json")
    public PersonDto getPersonData(@PathParam("personId") Long personId) throws RecordNotFoundException {
        return personService.getPersonData(personId);
    }

    @GET
    @Path("/restclient/{personId}")
    @Produces("application/json")
    public PersonDto getPersonThroughRestClient(@PathParam("personId") Long personId) throws RecordNotFoundException, RestClientCallException {
        return personService.getPersonThroughRestClient(personId);
    }

    @QND
    @GET
    @Path("/qnd/{firstName}")
    public Long qndCreatePerson(@PathParam("firstName") String firstName) {
        Person person = new Person();
        person.setFirstName(firstName);

        return personService.createPerson(person);
    }

    @QND
    @GET
    @Path("/qnd/{personId}/{firstName}")
    public Person qndUpdatePerson(@PathParam("personId") Long personId, @PathParam("firstName") String firstName) {
        Person person = new Person();
        person.setId(personId);
        person.setFirstName(firstName);

        return personService.updatePerson(person);
    }
}
