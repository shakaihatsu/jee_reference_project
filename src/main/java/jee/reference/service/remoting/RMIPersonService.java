package jee.reference.service.remoting;

import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jee.reference.exception.ApplicationInitializationException;
import jee.reference.exception.RecordNotFoundException;
import jee.reference.exception.RestClientCallException;
import jee.reference.model.Person;
import jee.reference.model.dto.PersonDto;
import jee.reference.service.api.PersonService;
import jee.reference.service.dao.PersonServiceImpl;
import jee.reference.util.Logged;

@RMI
@Logged
@Stateless
public class RMIPersonService implements PersonService {
    private jee.reference.service.api.remote.PersonService remotePersonService;

    @PostConstruct
    public void init() {
        try {
            remotePersonService = lookupRemotePersonService();
        } catch (NamingException e) {
            throw new ApplicationInitializationException("Couldn't lookup remote " + jee.reference.service.api.remote.PersonService.class.getName() + "!", e);
        }
    }

    @Override
    public List<Person> getAllPerson() {
        return remotePersonService.getAllPerson();
    }

    @Override
    public Person getPerson(Long personId) throws RecordNotFoundException {
        return remotePersonService.getPerson(personId);
    }

    @Override
    public Long createPerson(Person person) {
        return remotePersonService.createPerson(person);
    }

    @Override
    public Person updatePerson(Person person) {
        return remotePersonService.updatePerson(person);
    }

    @Override
    public List<PersonDto> getAllPersonData() {
        return remotePersonService.getAllPersonData();
    }

    @Override
    public PersonDto getPersonData(Long personId) {
        return remotePersonService.getPersonData(personId);
    }

    @Override
    public PersonDto getPersonThroughRestClient(Long personId) throws RestClientCallException {
        return remotePersonService.getPersonThroughRestClient(personId);
    }

    private jee.reference.service.api.remote.PersonService lookupRemotePersonService() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        final Context context = new InitialContext(jndiProperties);
        // The app name is the application name of the deployed EJBs.
        // This is typically the ear name without the .ear suffix.
        // However, the application name could be overridden in the application.xml of the EJB deployment on the server.
        // If the application (containing the EJB) is not deployed as a .ear, the app name should be empty.
        final String appName = "";
        // This is the module name of the deployed EJBs on the server.
        // This is typically the jar name of the EJB deployment, without the .jar suffix, or the war name, if the EJBs were deployed as part of a war.
        // It can be overridden via the ejb-jar.xml.
        final String moduleName = "jee_reference_project";
        // AS7 allows each deployment to have an (optional) distinct name.
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class.
        final String beanName = PersonServiceImpl.class.getSimpleName();
        // The remote view fully qualified class name.
        final String viewClassName = jee.reference.service.api.remote.PersonService.class.getName();

        // Lookup
        jee.reference.service.api.remote.PersonService remotePersonService = (jee.reference.service.api.remote.PersonService) context.lookup("ejb:" + appName
                + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);

        return remotePersonService;
    }

}
