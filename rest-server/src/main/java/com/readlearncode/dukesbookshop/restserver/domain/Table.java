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

    @Min(1)
    @Max(100)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Min(1)
    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    public Table() {

    }

    public Table(Integer id, Integer numberOfSeats) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (!Objects.equals(id, table.id)) {
            return false;
        }

        return Objects.equals(numberOfSeats, table.numberOfSeats);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + numberOfSeats.hashCode();
        return result;
    }
}
