package jee.reference.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Car {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person owner;

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car [id=" + id + ", version=" + version + ", ownerId=" + (owner == null ? "N/A" : owner.getId()) + "]";
    }
}
