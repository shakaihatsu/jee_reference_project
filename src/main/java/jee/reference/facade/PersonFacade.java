package jee.reference.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.model.Person;
import jee.reference.model.dto.PersonDto;
import jee.reference.service.RetryOnOptimisticLockException;
import jee.reference.service.api.PersonService;
import jee.reference.util.Logged;

@Logged
@ApplicationScoped
public class PersonFacade {
    @Inject
    private PersonService personService;

    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    public Person getPerson(Long personId) throws RecordNotFoundException {
        return personService.getPerson(personId);
    }

    public Long createPerson(Person person) {
        return personService.createPerson(person);
    }

    @RetryOnOptimisticLockException
    public Person updatePerson(Long personId, Person person) {
        return personService.updatePerson(personId, person);
    }

    public List<PersonDto> getAllPersonData() {
        return personService.getAllPersonData();
    }

    public PersonDto getPersonData(Long personId) {
        return personService.getPersonData(personId);
    }

    public PersonDto getPersonThroughRestClient(Long personId) throws RestClientCallException {
        return personService.getPersonThroughRestClient(personId);
    }
}
