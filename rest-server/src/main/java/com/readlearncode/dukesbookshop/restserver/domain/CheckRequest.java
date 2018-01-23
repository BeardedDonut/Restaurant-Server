package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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


    @NotNull
    private Customer customer;

    @NotNull
    private TimeSpan ts;

    @NotNull
    private Date date;

    @NotNull
    private Integer seats;

    public CheckRequest() {

    }
}
