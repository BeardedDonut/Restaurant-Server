package com.readlearncode.dukesbookshop.restserver.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by navid on 11/24/17.
 * Reservation Bean
 */
@XmlRootElement
public class Reservation implements Serializable {


    private Integer reservationId;

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer tableId;

    @NotNull
    private Date reservationDate;

    @NotNull
    private TimeSpan reservationTime;

    @Size(max = 30)
    private String otherRequirements;

    public Reservation() {

    }

    public Reservation(int reservationId, int customerId, int tableId, Date reservationDate, TimeSpan reservationTime, String otherRequirements) {
        this.reservationId = reservationId;
        this.tableId = tableId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.otherRequirements = otherRequirements;
    }

    public boolean doesTimeSpanConflicts(TimeSpan ts) {
        int myTimeSpanEnd = Integer.parseInt(this.reservationTime.getEnd());
        int givenTimeSpanStart = Integer.parseInt(ts.getStart());

        if (myTimeSpanEnd > givenTimeSpanStart) {
            return true;
        }

        return false;
    }

    public boolean tableIdMatches(int tableId) {
        return this.tableId == tableId;
    }

    public void udpateTable(int tableId) {
        //TODO: to change the table for customer
    }

    public void updateTime(Date newDate, TimeSpan newTimeSpan) {
        //TODO: update the reservation time
    }

    public void updateRequirements() {
        //TODO: update the requirements
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (!Objects.equals(reservationId, that.reservationId)) return false;
        if (!Objects.equals(customerId, that.customerId)) return false;
        if (!Objects.equals(tableId, that.tableId)) return false;
        if (reservationDate != null ? !reservationDate.equals(that.reservationDate) : that.reservationDate != null)
            return false;
        if (reservationTime != null ? !reservationTime.equals(that.reservationTime) : that.reservationTime != null)
            return false;
        return otherRequirements != null ? otherRequirements.equals(that.otherRequirements) : that.otherRequirements == null;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + customerId;
        result = 31 * result + tableId;
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        result = 31 * result + (reservationTime != null ? reservationTime.hashCode() : 0);
        result = 31 * result + (otherRequirements != null ? otherRequirements.hashCode() : 0);
        return result;
    }
}