package pl.com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "country_code")
    private String countryCode;

    @OneToMany(mappedBy = "loadingPlace")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "unloadingPlace")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> orders = new HashSet<>();

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

    public Address name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public Address number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZip() {
        return zip;
    }

    public Address zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Address countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Address orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Address addOrder(Order order) {
        this.orders.add(order);
        order.setLoadingPlace(this);
        return this;
    }

    public Address removeOrder(Order order) {
        this.orders.remove(order);
        order.setLoadingPlace(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Address orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Address addOrder(Order order) {
        this.orders.add(order);
        order.setUnloadingPlace(this);
        return this;
    }

    public Address removeOrder(Order order) {
        this.orders.remove(order);
        order.setUnloadingPlace(null);
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
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", street='" + getStreet() + "'" +
            ", number=" + getNumber() +
            ", zip='" + getZip() + "'" +
            ", city='" + getCity() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}
