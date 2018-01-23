package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by navid on 11/24/17.
 * CheckRequest Bean
 */
@XmlRootElement
@Entity(name = "checkRequest")
@javax.persistence.Table(name = "checkRequest")
public class CheckRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    private Date date;

    @NotNull
    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer relatedCustomer;

    @NotNull
    @Embedded
    private TimeSpan ts;

    public CheckRequest() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Customer getRelatedCustomer() {
        return relatedCustomer;
    }

    public void setRelatedCustomer(Customer relatedCustomer) {
        this.relatedCustomer = relatedCustomer;
    }

    public TimeSpan getTs() {
        return ts;
    }

    public void setTs(TimeSpan ts) {
        this.ts = ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckRequest that = (CheckRequest) o;

        if (id != that.id) return false;
        if (!date.equals(that.date)) return false;
        if (!numberOfSeats.equals(that.numberOfSeats)) return false;
        if (!relatedCustomer.equals(that.relatedCustomer)) return false;
        return ts.equals(that.ts);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + numberOfSeats.hashCode();
        result = 31 * result + relatedCustomer.hashCode();
        result = 31 * result + ts.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CheckRequest{" +
                "id=" + id +
                ", date=" + date +
                ", numberOfSeats=" + numberOfSeats +
                ", relatedCustomer=" + relatedCustomer +
                ", ts=" + ts +
                '}';
    }
}
