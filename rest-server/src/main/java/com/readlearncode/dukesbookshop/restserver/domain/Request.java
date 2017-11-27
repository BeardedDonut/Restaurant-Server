package com.readlearncode.dukesbookshop.restserver.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by navid on 11/24/17.
 * Request Bean
 */
@XmlRootElement
public class Request implements Serializable {

    @Min(1)
    @Max(100)
    @NotNull
    private Integer customerId;

    @NotNull
    private TimeSpan ts;

    @NotNull
    private Date date;

    @NotNull
    private Integer seats;

    public Request() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public TimeSpan getTs() {
        return ts;
    }

    public void setTs(TimeSpan ts) {
        this.ts = ts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (customerId != request.customerId) return false;
        if (seats != request.seats) return false;
        if (ts != null ? !ts.equals(request.ts) : request.ts != null) return false;
        return date != null ? date.equals(request.date) : request.date == null;
    }

    @Override
    public int hashCode() {
        int result = customerId;
        result = 31 * result + (ts != null ? ts.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + seats;
        return result;
    }
}
