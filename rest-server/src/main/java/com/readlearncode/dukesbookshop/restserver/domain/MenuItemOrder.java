package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created by navid on 1/27/18.
 */
@Entity
@javax.persistence.Table(name = "menuItemOrder")
@AssociationOverrides({
        @AssociationOverride
                (name = "pk.order", joinColumns = @JoinColumn(name = "orderId")),
        @AssociationOverride
                (name = "pk.menuItem", joinColumns = @JoinColumn(name = "menuItemId"))
})
public class MenuItemOrder implements Serializable {

    @EmbeddedId
    private MenuItemOrderId pk = new MenuItemOrderId();

    private String name;
    private int menuItem;
    private float price;
    private Integer number = 0;


    @XmlTransient
    public MenuItem getMenuItem() {
        return getPk().getMenuItem();
    }

    public void setMenuItem(MenuItem mi) {
        this.name = mi.getName();
        this.price = mi.getPrice();
        getPk().setMenuItem(mi);
    }

    @XmlTransient
    public Order getOrder() {
        return getPk().getOrder();
    }

    public void setOrder(Order order) {
        getPk().setOrder(order);
    }

    public MenuItemOrder() {
    }

    public MenuItemOrderId getPk() {
        return pk;
    }

    public void setPk(MenuItemOrderId pk) {
        this.pk = pk;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItemOrder that = (MenuItemOrder) o;

        if (number != that.number) return false;
        return pk.equals(that.pk);
    }

    @Override
    public int hashCode() {
        int result = pk.hashCode();
        result = 31 * result + number;
        return result;
    }
}
