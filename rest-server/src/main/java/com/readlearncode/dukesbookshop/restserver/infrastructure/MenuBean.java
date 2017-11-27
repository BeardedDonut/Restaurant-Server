package com.readlearncode.dukesbookshop.restserver.infrastructure;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */

@Stateless
public class MenuBean implements Menu {

    private ArrayList<MenuItem> drinks = new ArrayList<MenuItem>();
    private ArrayList<MenuItem> foods = new ArrayList<MenuItem>();
    private ArrayList<MenuItem> desserts = new ArrayList<MenuItem>();


    @Override
    public ArrayList<MenuItem> getDrinks() {
        return drinks;
    }

    @Override
    public ArrayList<MenuItem> getFoods() {
        return foods;
    }

    @Override
    public ArrayList<MenuItem> getDesserts() {
        return desserts;
    }

    @Override
    public Optional<MenuItem> addToDrinks(MenuItem newItem) {
        drinks.add(newItem);
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> addToFoods(MenuItem newItem) {
        foods.add(newItem);
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> addToDesserts(MenuItem newItem) {
        desserts.add(newItem);
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> updateDrinkByName(String name, MenuItem newItem) {
        for (MenuItem itm : drinks) {
            if (itm.getName().equals(name)) {
                drinks.remove(itm);
                drinks.add(newItem);
            }
        }
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> updateFoodByName(String name, MenuItem newItem) {
        for (MenuItem itm : foods) {
            if (itm.getName().equals(name)) {
                foods.remove(itm);
                foods.add(newItem);
            }
        }
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> updateDessertByName(String name, MenuItem newItem) {
        for (MenuItem itm : desserts) {
            if (itm.getName().equals(name)) {
                desserts.remove(itm);
                desserts.add(newItem);
            }
        }
        return Optional.of(newItem);
    }

    @Override
    public Optional<MenuItem> findMenuItemByName(String name) {
        //TODO find a way to remove the overhead of search

        for (MenuItem itm : drinks) {
            if (itm.getName().equals(name)) {
                return Optional.of(itm);
            }
        }


        for (MenuItem itm : desserts) {
            if (itm.getName().equals(name)) {
                return Optional.of(itm);
            }
        }


        for (MenuItem itm : foods) {
            if (itm.getName().equals(name)) {
                return Optional.of(itm);
            }
        }

        return Optional.ofNullable(null);
    }

}
