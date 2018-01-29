package com.readlearncode.dukesbookshop.restserver.dataTransferObjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by navid on 1/30/18.
 */

@XmlRootElement
public class MenuItemOrderDTO implements Serializable {
    private String name;

    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItemOrderDTO that = (MenuItemOrderDTO) o;

        if (number != that.number) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }
}
