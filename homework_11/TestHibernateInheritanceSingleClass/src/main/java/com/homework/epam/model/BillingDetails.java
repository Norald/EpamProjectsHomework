package com.homework.epam.model;

import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;

@Entity
@Table(name = "billingdetails")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bd_type")
@DiscriminatorFormula("CASE WHEN card_number IS NOT NULL THEN 'CC' ELSE 'BA' END")
public abstract class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    Owner owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
