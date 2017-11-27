package com.readlearncode.dukesbookshop.restserver.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by navid on 11/24/17.
 * Customer Bean
 */

@XmlRootElement
public class Customer implements Serializable {

    @Min(0)
    @Max(120)
    private Integer customerId;

    @NotNull
    private String fullName;

    @NotNull
    private String telephoneNumber;

    //TODO: add authentication properties

    public Customer() {

    }

    public Customer(Integer id, String name, String telephoneNumber) {
        this.customerId = id;
        this.fullName = name;
        this.telephoneNumber = telephoneNumber;
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

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(customerId, customer.customerId)) return false;
        if (fullName != null ? !fullName.equals(customer.fullName) : customer.fullName != null) return false;
        return telephoneNumber != null ? telephoneNumber.equals(customer.telephoneNumber) : customer.telephoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
        return result;
    }
}