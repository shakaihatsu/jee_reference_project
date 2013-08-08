package jee.reference.decorator;

import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.meta.POI;
import jee.reference.meta.POITag;
import jee.reference.model.Person;
import jee.reference.model.dto.PersonDto;
import jee.reference.service.api.PersonService;
import jee.reference.service.messaging.MessagingService;

@POI(
    tags = { POITag.DECORATOR, POITag.BE_CAREFUL },
    value = "If an application has both interceptors and decorators, the interceptors are invoked first. This means, in effect, that you cannot intercept a decorator.")
@Decorator
public class PersonServiceJMSDecorator implements PersonService {

    @Delegate
    @Inject
    @Any
    private PersonService delegate;

    @Inject
    private MessagingService messagingService;

    @Override
    public List<Person> getAllPerson() {
        return delegate.getAllPerson();
    }

    @Override
    public Person getPerson(Long personId) throws RecordNotFoundException {
        return delegate.getPerson(personId);
    }

    @Override
    public Long createPerson(Person person) {
        messagingService.sendTextMessage("Person created (message sent outside of transaction):" + person);
        Long result = delegate.createPerson(person);
        return result;
    }

    @Override
    public Person updatePerson(Person person) {
        return delegate.updatePerson(person);
    }

    @Override
    public List<PersonDto> getAllPersonData() {
        return delegate.getAllPersonData();
    }

    @Override
    public PersonDto getPersonData(Long personId) {
        return delegate.getPersonData(personId);
    }

    @Override
    public PersonDto getPersonThroughRestClient(Long personId) throws RestClientCallException {
        return delegate.getPersonThroughRestClient(personId);
    }

}
