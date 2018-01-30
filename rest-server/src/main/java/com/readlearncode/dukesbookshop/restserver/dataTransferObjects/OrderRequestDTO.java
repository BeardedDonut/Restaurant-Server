package com.readlearncode.dukesbookshop.restserver.dataTransferObjects;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by navid on 1/29/18.
 */
@XmlRootElement
public class OrderRequestDTO implements Serializable {

    @NotNull
    private int reservationId;

    @NotNull
    private int orderId;

    private List<MenuItemOrderDTO> orderedItems;


    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<MenuItemOrderDTO> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<MenuItemOrderDTO> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderRequestDTO that = (OrderRequestDTO) o;

        if (reservationId != that.reservationId) return false;
        if (orderId != that.orderId) return false;
        return orderedItems != null ? orderedItems.equals(that.orderedItems) : that.orderedItems == null;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + orderId;
        result = 31 * result + (orderedItems != null ? orderedItems.hashCode() : 0);
        return result;
    }
}

