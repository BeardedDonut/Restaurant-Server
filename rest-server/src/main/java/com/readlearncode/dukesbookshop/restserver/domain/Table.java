package com.readlearncode.dukesbookshop.restserver.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by navid on 11/24/17.
 * Table Bean
 */
@XmlRootElement
public class Table implements Serializable {

    @Min(1)
    @Max(100)
    @NotNull
    private Integer tableId;

    @Min(1)
    private Integer numberOfSeats;

    public Table() {

    }

    public Table(Integer id, Integer numberOfSeats) {
        this.tableId = id;
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (!Objects.equals(tableId, table.tableId)) {
            return false;
        }

        return Objects.equals(numberOfSeats, table.numberOfSeats);
    }

    @Override
    public int hashCode() {
        int result = tableId;
        result = 31 * result + numberOfSeats;
        return result;
    }
}
