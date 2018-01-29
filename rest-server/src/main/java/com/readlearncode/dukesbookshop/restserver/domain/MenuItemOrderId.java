package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created by navid on 1/27/18.
 */

@Embeddable
public class MenuItemOrderId implements Serializable {

    @ManyToOne
    private Order order;

    @ManyToOne
    private MenuItem menuItem;

    public MenuItemOrderId() {
    }

    @XmlTransient
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @XmlTransient
    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItemOrderId that = (MenuItemOrderId) o;

        if (!order.equals(that.order)) return false;
        return menuItem.equals(that.menuItem);
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (menuItem != null ? menuItem.hashCode() : 0);
        return result;
    }
}
