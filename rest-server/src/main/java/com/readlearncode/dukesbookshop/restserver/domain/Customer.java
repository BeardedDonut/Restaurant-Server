package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by navid on 11/24/17.
 * Customer POJO
 */

@XmlRootElement
@Entity(name = "customer")
@javax.persistence.Table(name = "customer")
public class Customer implements Serializable {

    //<editor-fold desc="properties">
    @Min(0)
    @Max(120)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer customerId;

    @NotNull
    @Column(name = "fullName")
    private String fullName;

    @NotNull
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "relatedCustomer", fetch = FetchType.EAGER)
    private List<CheckRequest> myCheckRequests;

    @OneToMany(mappedBy = "relatedCustomer", fetch = FetchType.EAGER)
    private List<Reservation> myReservations;

    //</editor-fold>

    //TODO: add authentication properties

    //<editor-fold desc="constructors">
    public Customer() {

    }

    public Customer(Integer id, String name, String phoneNumber) {
        this.customerId = id;
        this.fullName = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String name, String phoneNumber) {
        this.fullName = name;
        this.phoneNumber = phoneNumber;
    }
    //</editor-fold>

    //<editor-fold desc="setters and getters">
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<CheckRequest> getMyCheckRequests() {
        return myCheckRequests;
    }

    public void setMyCheckRequests(List<CheckRequest> myCheckRequests) {
        this.myCheckRequests = myCheckRequests;
    }

    public List<Reservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<Reservation> myReservations) {
        this.myReservations = myReservations;
    }
    //</editor-fold desc="properties">


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != null ? !customerId.equals(customer.customerId) : customer.customerId != null) return false;
        if (!fullName.equals(customer.fullName)) return false;
        if (!phoneNumber.equals(customer.phoneNumber)) return false;
        if (myCheckRequests != null ? !myCheckRequests.equals(customer.myCheckRequests) : customer.myCheckRequests != null)
            return false;
        return myReservations != null ? myReservations.equals(customer.myReservations) : customer.myReservations == null;
    }

    @Override
    public int hashCode() {
        int result = customerId != null ? customerId.hashCode() : 0;
        result = 31 * result + fullName.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + (myCheckRequests != null ? myCheckRequests.hashCode() : 0);
        result = 31 * result + (myReservations != null ? myReservations.hashCode() : 0);
        return result;
    }
}