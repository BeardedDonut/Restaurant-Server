package com.readlearncode.dukesbookshop.restserver.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by navid on 11/26/17.
 * Menu Item POJO
 */
@XmlRootElement
@Entity(name = "menuItem")
@javax.persistence.Table(name = "menuItem")
public class MenuItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "price")
    private Float price;

    @NotNull
    @Column(name = "imageFileName")
    private String imageFileName;

    @Enumerated(EnumType.STRING)
    private MenuItemCategory category;

    public MenuItem() {

    }

    public MenuItem(String name, Float price, String imageFileName) {
        this.name = name;
        this.price = price;
        this.imageFileName = imageFileName;
    }

    public MenuItem(String name, Float price) {
        this(name, price, "somewhere far far away");
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

        MenuItem menuItem = (MenuItem) o;

        if (name != null ? !name.equals(menuItem.name) : menuItem.name != null) return false;
        if (price != null ? !price.equals(menuItem.price) : menuItem.price != null) return false;
        return imageFileName != null ? imageFileName.equals(menuItem.imageFileName) : menuItem.imageFileName == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (imageFileName != null ? imageFileName.hashCode() : 0);
        return result;
    }

    public MenuItemCategory getCategory() {
        return category;
    }

    public void setCategory(MenuItemCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder x = new StringBuilder();

        x.append("Menu Item---");
        x.append("name:" + name + "\n");
        x.append("price:" + price + "\n");
        x.append("imageFileName:" + imageFileName + "\n");

        return x.toString();
    }
}
