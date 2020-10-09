package pl.com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contractor.
 */
@Entity
@Table(name = "contractor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contractor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vat_id")
    private String vatId;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> customerOrders = new HashSet<>();

    @OneToMany(mappedBy = "carrier")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> carrierOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Contractor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatId() {
        return vatId;
    }

    public Contractor vatId(String vatId) {
        this.vatId = vatId;
        return this;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Contractor contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contractor phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public Contractor address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Order> getCustomerOrders() {
        return customerOrders;
    }

    public Contractor customerOrders(Set<Order> customerOrders) {
        this.customerOrders = customerOrders;
        return this;
    }

    public Contractor addCustomerOrder(Order customerOrder) {
        this.customerOrders.add(customerOrder);
        customerOrder.setCustomer(this);
        return this;
    }

    public Contractor removeCustomerOrder(Order customerOrder) {
        this.customerOrders.remove(customerOrder);
        customerOrder.setCustomer(null);
        return this;
    }

    public void setCustomerOrders(Set<Order> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Set<Order> getCarrierOrders() {
        return carrierOrders;
    }

    public Contractor carrierOrders(Set<Order> carrierOrders) {
        this.carrierOrders = carrierOrders;
        return this;
    }

    public Contractor addCarrierOrder(Order carrierOrder) {
        this.carrierOrders.add(carrierOrder);
        carrierOrder.setCarrier(this);
        return this;
    }

    public Contractor removeCarrierOrder(Order carrierOrder) {
        this.carrierOrders.remove(carrierOrder);
        carrierOrder.setCarrier(null);
        return this;
    }

    public void setCarrierOrders(Set<Order> carrierOrders) {
        this.carrierOrders = carrierOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contractor)) {
            return false;
        }
        return id != null && id.equals(((Contractor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contractor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vatId='" + getVatId() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
