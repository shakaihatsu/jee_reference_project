package jee.reference.service.api;

import java.util.List;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.model.Person;
import jee.reference.model.dto.PersonDto;

public interface PersonService {
    List<Person> getAllPerson();

    Person getPerson(Long personId) throws RecordNotFoundException;

    Long createPerson(Person person);

    Person updatePerson(Person person);

    List<PersonDto> getAllPersonData();

    PersonDto getPersonData(Long personId);

    PersonDto getPersonThroughRestClient(Long personId) throws RestClientCallException;

}