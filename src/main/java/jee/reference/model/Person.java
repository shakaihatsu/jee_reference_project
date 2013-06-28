package jee.reference.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import jee.reference.meta.POI;
import jee.reference.meta.POITag;
import jee.reference.meta.TODO;
import jee.reference.meta.TODOTag;
import jee.reference.util.DateDeserializer;
import jee.reference.util.DateSerializer;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NamedQueries({
    @NamedQuery(name = Person.NQ_GET_ALL_PERSON_DATA, query = "SELECT NEW jee.reference.model.dto.PersonDto("
        + " person.firstName, person.lastName, person.dateOfBirth, drivingLicence.documentId, passport.documentId"
        + " ) FROM Person person, DrivingLicence drivingLicence, Passport passport"
        + " WHERE person.deleted = :deleted AND drivingLicence.owner = person AND passport.owner = person"),
    @NamedQuery(name = Person.NQ_GET_PERSON_DATA, query = "SELECT NEW jee.reference.model.dto.PersonDto("
        + " person.firstName, person.lastName, person.dateOfBirth, drivingLicence.documentId, passport.documentId"
        + " ) FROM Person person LEFT OUTER JOIN person.drivingLicence drivingLicence LEFT OUTER JOIN person.passport passport"
        + " WHERE person.id = :personId"), })
@Entity
@SequenceGenerator(name = "personIdSequenceGenerator", sequenceName = "SEQ_PERSON_ID", initialValue = 1, allocationSize = 10)
public class Person {
    public static final String NQ_GET_ALL_PERSON_DATA = "NQ_GET_ALL_PERSON_DATA";
    public static final String NQ_GET_PERSON_DATA = "NQ_GET_PERSON_DATA";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personIdSequenceGenerator")
    @Column(name = "PERSON_ID")
    private Long id;

    @Version
    private Long version;

    @TODO(tags = { TODOTag.MAY_CHANGE_IN_THE_FUTURE, TODOTag.JPA_2_1 }, value = "Probably should use @Converter in JPA 2.1")
    private Long deleted = 0L;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Integer favoriteNumber;

    @POI(tags = { POITag.STRANGE_BEHAVIOUR }, value = "OneToOne doesn't cache well when null. Hibernate is going to issue a select in that case.")
    @OneToOne(targetEntity = Passport.class, mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Passport passport;

    @OneToOne(targetEntity = DrivingLicence.class, mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private DrivingLicence drivingLicence;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Set<Car> cars;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "J_PERSON_ADDRESS", joinColumns = { @JoinColumn(name = "PERSON_ID") }, inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID") })
    private Set<Address> addresses;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Set<CreditCard> creditCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(Integer favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public DrivingLicence getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(DrivingLicence drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
        for (Car car : cars) {
            car.setOwner(this);
        }
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", version=" + version + ", deleted=" + deleted + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
            + dateOfBirth + ", favoriteNumber=" + favoriteNumber + ", passport=" + passport + ", drivingLicence=" + drivingLicence + ", cars=" + cars
            + ", addresses=" + addresses + ", creditCards=" + creditCards + "]";
    }

}
