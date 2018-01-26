package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by navid on 11/24/17.
 * Reservation Bean
 */
@XmlRootElement
@Entity(name = "reservation")
@javax.persistence.Table(name = "reservation")
public class Reservation implements Serializable {

    // <editor-fold desc="properties">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "submissionDate")
    private Date submissionDate;

    @NotNull
    private Date reservationDate;

    @NotNull
    @Embedded
    private TimeSpan reservationTime;

    @Size(max = 255)
    private String otherRequirements;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer relatedCustomer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tableId")
    private Table relatedTable;

    //</editor-fold>

    // <editor-fold desc="constructors">
    public Reservation() {

    }

    public Reservation(int id, Customer relatedCustomer, Table relatedTable, Date reservationDate, TimeSpan reservationTime, String otherRequirements) {
        this.id = id;
        this.relatedCustomer = relatedCustomer;
        this.relatedTable = relatedTable;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.otherRequirements = otherRequirements;
    }
    // </editor-fold>

    // <editor-fold desc="setters and getters">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public TimeSpan getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(TimeSpan reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Customer getRelatedCustomer() {
        return relatedCustomer;
    }

    public void setRelatedCustomer(Customer relatedCustomer) {
        this.relatedCustomer = relatedCustomer;
    }

    public Table getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(Table relatedTable) {
        this.relatedTable = relatedTable;
    }

    // </editor-fold>


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!reservationDate.equals(that.reservationDate)) return false;
        if (!reservationTime.equals(that.reservationTime)) return false;
        if (otherRequirements != null ? !otherRequirements.equals(that.otherRequirements) : that.otherRequirements != null)
            return false;
        if (!submissionDate.equals(that.submissionDate)) return false;
        if (!relatedCustomer.equals(that.relatedCustomer)) return false;
        return relatedTable.equals(that.relatedTable);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + reservationDate.hashCode();
        result = 31 * result + reservationTime.hashCode();
        result = 31 * result + (otherRequirements != null ? otherRequirements.hashCode() : 0);
        result = 31 * result + submissionDate.hashCode();
        result = 31 * result + relatedCustomer.hashCode();
        result = 31 * result + relatedTable.hashCode();
        return result;
    }

    public boolean doesTimeSpanConflicts(TimeSpan ts) {
        int myTimeSpanEnd = Integer.parseInt(this.reservationTime.getEnd());
        int myTimeSpanStart = Integer.parseInt(this.reservationTime.getStart());

        int givenTimeSpanStart = Integer.parseInt(ts.getStart());
        int givenTimeSpanEnd = Integer.parseInt(ts.getEnd());

        boolean endTimeIntersection = givenTimeSpanEnd <= myTimeSpanEnd && givenTimeSpanEnd >= myTimeSpanStart;
        boolean startTimeIntersection = givenTimeSpanStart >= myTimeSpanStart && givenTimeSpanStart <= myTimeSpanEnd;

        return endTimeIntersection || startTimeIntersection;

    }
}