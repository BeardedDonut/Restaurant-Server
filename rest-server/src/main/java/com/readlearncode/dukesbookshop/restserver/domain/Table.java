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
 * Table POJO
 */
@XmlRootElement
@Entity(name = "restaurantTable")
@javax.persistence.Table(name = "restaurantTable")
public class Table implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "numberOfSeats")
    @Min(2)
    private Integer numberOfSeats;

    public Table() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (id != null ? !id.equals(table.id) : table.id != null) return false;
        return numberOfSeats.equals(table.numberOfSeats);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + numberOfSeats.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
