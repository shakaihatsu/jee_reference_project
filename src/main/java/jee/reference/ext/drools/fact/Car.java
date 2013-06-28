package jee.reference.ext.drools.fact;

public class Car {
    private Long id;

    private Person owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car [id=" + id + ", ownerId=" + (owner == null ? "N/A" : owner.getId()) + "]";
    }

}
