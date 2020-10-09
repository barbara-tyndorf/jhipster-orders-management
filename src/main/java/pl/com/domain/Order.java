package pl.com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

import pl.com.domain.enumeration.Currency;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "customer_price", precision = 21, scale = 2)
    private BigDecimal customerPrice;

    @Column(name = "carrier_price", precision = 21, scale = 2)
    private BigDecimal carrierPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_currency")
    private Currency customerCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier_currency")
    private Currency carrierCurrency;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Contractor customer;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Contractor carrier;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Forwarder forwarder;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Address loadingPlace;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Address unloadingPlace;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCustomerPrice() {
        return customerPrice;
    }

    public Order customerPrice(BigDecimal customerPrice) {
        this.customerPrice = customerPrice;
        return this;
    }

    public void setCustomerPrice(BigDecimal customerPrice) {
        this.customerPrice = customerPrice;
    }

    public BigDecimal getCarrierPrice() {
        return carrierPrice;
    }

    public Order carrierPrice(BigDecimal carrierPrice) {
        this.carrierPrice = carrierPrice;
        return this;
    }

    public void setCarrierPrice(BigDecimal carrierPrice) {
        this.carrierPrice = carrierPrice;
    }

    public Currency getCustomerCurrency() {
        return customerCurrency;
    }

    public Order customerCurrency(Currency customerCurrency) {
        this.customerCurrency = customerCurrency;
        return this;
    }

    public void setCustomerCurrency(Currency customerCurrency) {
        this.customerCurrency = customerCurrency;
    }

    public Currency getCarrierCurrency() {
        return carrierCurrency;
    }

    public Order carrierCurrency(Currency carrierCurrency) {
        this.carrierCurrency = carrierCurrency;
        return this;
    }

    public void setCarrierCurrency(Currency carrierCurrency) {
        this.carrierCurrency = carrierCurrency;
    }

    public Contractor getCustomer() {
        return customer;
    }

    public Order customer(Contractor contractor) {
        this.customer = contractor;
        return this;
    }

    public void setCustomer(Contractor contractor) {
        this.customer = contractor;
    }

    public Contractor getCarrier() {
        return carrier;
    }

    public Order carrier(Contractor contractor) {
        this.carrier = contractor;
        return this;
    }

    public void setCarrier(Contractor contractor) {
        this.carrier = contractor;
    }

    public Forwarder getForwarder() {
        return forwarder;
    }

    public Order forwarder(Forwarder forwarder) {
        this.forwarder = forwarder;
        return this;
    }

    public void setForwarder(Forwarder forwarder) {
        this.forwarder = forwarder;
    }

    public Address getLoadingPlace() {
        return loadingPlace;
    }

    public Order loadingPlace(Address address) {
        this.loadingPlace = address;
        return this;
    }

    public void setLoadingPlace(Address address) {
        this.loadingPlace = address;
    }

    public Address getUnloadingPlace() {
        return unloadingPlace;
    }

    public Order unloadingPlace(Address address) {
        this.unloadingPlace = address;
        return this;
    }

    public void setUnloadingPlace(Address address) {
        this.unloadingPlace = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", customerPrice=" + getCustomerPrice() +
            ", carrierPrice=" + getCarrierPrice() +
            ", customerCurrency='" + getCustomerCurrency() + "'" +
            ", carrierCurrency='" + getCarrierCurrency() + "'" +
            "}";
    }
}
