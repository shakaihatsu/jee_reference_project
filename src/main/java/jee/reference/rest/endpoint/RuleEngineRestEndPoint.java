package jee.reference.rest.endpoint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jee.reference.exception.InternalServerErrorException;
import jee.reference.meta.QND;
import jee.reference.model.Address;
import jee.reference.model.Car;
import jee.reference.model.Person;
import jee.reference.service.drools.DroolsService;
import jee.reference.util.Logged;

import org.modelmapper.ModelMapper;

@Logged
@Path("/rules")
@RequestScoped
public class RuleEngineRestEndPoint {
    @Inject
    private DroolsService droolsService;
    @Inject
    private ModelMapper modelMapper;

    @QND
    @GET
    @Path("/qnd/{firstName}/{deleted}")
    @Produces("application/json")
    public Set<jee.reference.ext.drools.fact.Person> qndRunRules(@PathParam("firstName") String firstName, @PathParam("deleted") Long deleted)
            throws InternalServerErrorException {
        Person person = createPerson(firstName, deleted);

        jee.reference.ext.drools.fact.Person personFact = convertToFact(person);

        Set<jee.reference.ext.drools.fact.Person> personFactSet = new HashSet<jee.reference.ext.drools.fact.Person>(Arrays.asList(personFact));
        List<Object> factList = Arrays.asList(personFactSet, personFact);

        droolsService.executeInStatelessKnowledgeSession(factList);

        convertFromFact(personFact);

        return personFactSet;
    }

    private Person createPerson(String firstName, Long deleted) {
        Person person = new Person();
        person.setDeleted(deleted);
        person.setFirstName(firstName);

        Set<Car> cars = new HashSet<Car>();
        person.setCars(cars);

        Set<Address> addresses = new HashSet<Address>();
        person.setAddresses(addresses);

        return person;
    }

    private jee.reference.ext.drools.fact.Person convertToFact(Person person) {
        return modelMapper.map(person, jee.reference.ext.drools.fact.Person.class);
    }

    private Person convertFromFact(jee.reference.ext.drools.fact.Person personFact) {
        return modelMapper.map(personFact, Person.class);
    }
}
