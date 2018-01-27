package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Created by navid on 11/26/17.
 */
@XmlRootElement
@Entity
@javax.persistence.Table(name = "foodOrder")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "totalCost")
    private Float totalCost;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservationId")
    private Reservation accordingReservation;

    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.menuItem")
    private Set<MenuItemOrder> menuItemOrders = new HashSet<MenuItemOrder>(0);

    //<editor-fold desc="constructor">
    public Order() {
    }

    public Order(int orderId, Reservation accordingReservation, HashMap<String, Integer> orderItems) {
        this.id = orderId;
        this.accordingReservation = accordingReservation;
        this.status = OrderStatus.ON_HOLD;
    }
    //</editor-fold>


    //<editor-fold desc="setter & getters">

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
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

    public Set<MenuItemOrder> getMenuItemOrders() {
        return menuItemOrders;
    }

    public void setMenuItemOrders(Set<MenuItemOrder> menuItemOrders) {
        this.menuItemOrders = menuItemOrders;
    }

    //</editor-fold>


    //<editor-fold desc="equals and hashcode">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!totalCost.equals(order.totalCost)) return false;
        if (!accordingReservation.equals(order.accordingReservation)) return false;
        if (status != order.status) return false;
        return menuItemOrders != null ? menuItemOrders.equals(order.menuItemOrders) : order.menuItemOrders == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (accordingReservation != null ? accordingReservation.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

//</editor-fold>

    @Override
    public String toString() {
        return "Order{" +
                ", orderId=" + id +
                ", totalCost=" + totalCost +
                ", accordingReservation=" + accordingReservation +
                ", status=" + status +
                '}';
    }
}
