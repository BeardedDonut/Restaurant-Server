package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder x = new StringBuilder();

        x.append("Customer---");
        x.append("Customer id: " + customerId + "\n");
        x.append("FullName: " + fullName + "\n");
        x.append("Phone Number: " + phoneNumber + "\n");

        return x.toString();
    }
}