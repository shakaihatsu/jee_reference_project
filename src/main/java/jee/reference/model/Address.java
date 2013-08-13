package jee.reference.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import jee.reference.util.ResidentsSerializer;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String streetName;

    @Enumerated(EnumType.STRING)
    private EStreetType streetType;

    private String zipOrPostalCode;

    @ManyToMany(mappedBy = "addresses")
    private Set<Person> residents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public EStreetType getStreetType() {
        return streetType;
    }

    public void setStreetType(EStreetType streetType) {
        this.streetType = streetType;
    }

    public String getZipOrPostalCode() {
        return zipOrPostalCode;
    }

    public void setZipOrPostalCode(String zipOrPostalCode) {
        this.zipOrPostalCode = zipOrPostalCode;
    }

    @JsonSerialize(using = ResidentsSerializer.class)
    public Set<Person> getResidents() {
        return residents;
    }

    public void setResidents(Set<Person> residents) {
        this.residents = residents;
    }

}
