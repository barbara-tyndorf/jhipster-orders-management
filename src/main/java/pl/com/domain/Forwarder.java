package pl.com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import pl.com.domain.enumeration.Branch;

/**
 * A Forwarder.
 */
@Entity
@Table(name = "forwarder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forwarder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary")
    private Long salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch")
    private Branch branch;

    @OneToMany(mappedBy = "forwarder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Forwarder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Forwarder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Forwarder email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Forwarder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public Forwarder hireDate(Instant hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return salary;
    }

    public Forwarder salary(Long salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Branch getBranch() {
        return branch;
    }

    public Forwarder branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Forwarder orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Forwarder addOrder(Order order) {
        this.orders.add(order);
        order.setForwarder(this);
        return this;
    }

    public Forwarder removeOrder(Order order) {
        this.orders.remove(order);
        order.setForwarder(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forwarder)) {
            return false;
        }
        return id != null && id.equals(((Forwarder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Forwarder{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", branch='" + getBranch() + "'" +
            "}";
    }
}
