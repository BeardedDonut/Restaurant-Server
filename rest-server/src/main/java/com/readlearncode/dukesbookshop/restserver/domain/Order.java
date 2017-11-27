package com.readlearncode.dukesbookshop.restserver.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by navid on 11/26/17.
 */
@XmlRootElement
public class Order implements Serializable {

    @NotNull
    private HashMap<String, Integer> orderItems;
    //e.g. <String, number of that item> -> <"Hamburger", 4>

    @Min(0)
    private int orderId;

    private int totalCost;

    @NotNull
    private Reservation accordingReservation;

    private OrderStatus status;

    //<editor-fold desc="constructor">
    public Order() {
    }

    public Order(int orderId, Reservation accordingReservation, HashMap<String, Integer> orderItems) {
        this.orderId = orderId;
        this.accordingReservation = accordingReservation;
        this.orderItems = orderItems;
        this.status = OrderStatus.ON_HOLD;
    }
    //</editor-fold>


    //<editor-fold desc="setter & getters">
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public HashMap<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(HashMap<String, Integer> orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Reservation getAccordingReservation() {
        return accordingReservation;
    }

    public void setAccordingReservation(Reservation accordingReservation) {
        this.accordingReservation = accordingReservation;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    //</editor-fold>


    //<editor-fold desc="equals and hashcode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (totalCost != order.totalCost) return false;
        if (orderItems != null ? !orderItems.equals(order.orderItems) : order.orderItems != null) return false;
        if (accordingReservation != null ? !accordingReservation.equals(order.accordingReservation) : order.accordingReservation != null)
            return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = orderItems != null ? orderItems.hashCode() : 0;
        result = 31 * result + totalCost;
        result = 31 * result + (accordingReservation != null ? accordingReservation.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    //</editor-fold>

    public enum OrderStatus {
        ON_HOLD, PREPARING, DELIVERED, PAYED
    }
}
