package jee.reference.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(CreditCardId.class)
public class CreditCard {
    @Id
    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person owner;

    @Id
    @Column
    private String bankName;
}
