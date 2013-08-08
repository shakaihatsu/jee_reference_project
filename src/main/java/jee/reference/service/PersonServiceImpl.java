package jee.reference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.external.spring.ConfigProperties;
import jee.reference.meta.TODO;
import jee.reference.meta.TODOTag;
import jee.reference.model.Person;
import jee.reference.model.Person_;
import jee.reference.model.dto.PersonDto;
import jee.reference.service.api.PersonService;
import jee.reference.service.decision.PersonDecisionBean;
import jee.reference.service.messaging.MessagingService;
import jee.reference.util.Admin;
import jee.reference.util.Logged;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

@Logged
@Stateless
public class PersonServiceImpl implements PersonService {
    @Inject
    @Admin
    private EntityManager entityManager;
    @Inject
    private PersonDecisionBean personDecisionBean;
    @Inject
    private ConfigProperties configProperties;
    @Inject
    private MessagingService messagingService;

    @Override
    public List<Person> getAllPersons() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        query.distinct(true);
        Root<Person> root = getJoinedPersonRoot(query);

        Predicate predicate = criteriaBuilder.equal(root.get(Person_.deleted), 0);
        query.select(root).where(predicate);

        List<Person> personList = entityManager.createQuery(query).setHint("org.hibernate.cacheable", true).getResultList();

        return personList;
    }

    @Override
    public Person getPerson(Long personId) throws RecordNotFoundException {
        Person person = entityManager.find(Person.class, personId);

        if (personDecisionBean.personDoesntExist(person)) {
            throw new RecordNotFoundException("Couldn't find " + Person.class.getSimpleName() + " with " + Person_.id.getName() + "=" + personId + "!");
        }

        return person;
    }

    @Override
    public Long createPerson(Person person) {
        entityManager.persist(person);
        messagingService.sendTextMessage("Person created (message sent as past of a transaction):" + person);

        return person.getId();
    }

    @Override
    public Person updatePerson(Person person) {
        Person updatedPerson = entityManager.merge(person);

        return updatedPerson;
    }

    @Override
    public List<PersonDto> getAllPersonData() {
        Query query = entityManager.createNamedQuery(Person.NQ_GET_ALL_PERSON_DATA);
        query.setParameter("deleted", false);

        @SuppressWarnings("unchecked")
        List<PersonDto> allPersonData = query.getResultList();

        return allPersonData;
    }

    @Override
    public PersonDto getPersonData(Long personId) {
        Query query = entityManager.createNamedQuery(Person.NQ_GET_PERSON_DATA);
        query.setParameter("personId", personId);
        PersonDto personData = (PersonDto) query.getSingleResult();

        return personData;
    }

    @Override
    public PersonDto getPersonThroughRestClient(Long personId) throws RestClientCallException {
        PersonDto personData;

        String url = buildUrl(configProperties.getHost(), configProperties.getPort(), configProperties.getPath(), personId);
        ClientRequest request = new ClientRequest(url);
        request.accept("application/json");

        try {
            ClientResponse<PersonDto> response = request.get(PersonDto.class);
            personData = response.getEntity();
        } catch (Exception e) {
            throw new RestClientCallException("Error occurred while calling '" + url + "' for a result of " + PersonDto.class.getSimpleName() + "!", e);
        }

        return personData;
    }

    @TODO(
        tags = { TODOTag.MAY_CHANGE_IN_THE_FUTURE, TODOTag.JPA_2_1 },
        value = "Might be able to use an EntityGraph from JPA 2.1 instead of the many fetches? Not sure that even if it is possible, this level of control shouldn't stay at this level.")
    private Root<Person> getJoinedPersonRoot(CriteriaQuery<Person> query) {
        Root<Person> root = query.from(Person.class);
        root.fetch(Person_.passport, JoinType.LEFT);
        root.fetch(Person_.drivingLicence, JoinType.LEFT);
        root.fetch(Person_.cars, JoinType.LEFT);
        root.fetch(Person_.addresses, JoinType.LEFT);

        return root;
    }

    private String buildUrl(String host, Long port, String path, Long personId) {
        return "http://" + host + ":" + port + path + personId;
    }
}
