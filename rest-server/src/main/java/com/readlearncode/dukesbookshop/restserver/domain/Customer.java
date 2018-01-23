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

    @OneToMany(mappedBy = "relatedCustomer", fetch = FetchType.LAZY)
    private List<CheckRequest> myCheckRequests;

    //TODO: add authentication properties

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(customerId, customer.customerId)) return false;
        if (fullName != null ? !fullName.equals(customer.fullName) : customer.fullName != null) return false;
        return phoneNumber != null ? phoneNumber.equals(customer.phoneNumber) : customer.phoneNumber == null;
    }


    public List<CheckRequest> getMyCheckRequests() {
        return myCheckRequests;
    }

    public void setMyCheckRequests(List<CheckRequest> myCheckRequests) {
        this.myCheckRequests = myCheckRequests;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", myCheckRequests=" + myCheckRequests +
                '}';
    }
}