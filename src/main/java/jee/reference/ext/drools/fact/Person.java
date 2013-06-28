package jee.reference.ext.drools.fact;

import java.util.Date;
import java.util.Set;

public class Person {
    private Long id;

    private Long deleted;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Integer favoriteNumber;

    private Passport passport;

    private Set<Car> cars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
        for (Car car : cars) {
            car.setOwner(this);
        }
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", deleted=" + deleted + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
            + ", favoriteNumber=" + favoriteNumber + ", passport=" + passport + ", cars=" + cars + "]";
    }

}
