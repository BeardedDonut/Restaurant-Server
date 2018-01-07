package com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface Menu {

    ArrayList<MenuItem> getDrinks();

    ArrayList<MenuItem> getFoods();

    ArrayList<MenuItem> getDesserts();

    Optional<MenuItem> addToDrinks(MenuItem newItem);

    Optional<MenuItem> addToFoods(MenuItem newItem);

    Optional<MenuItem> addToDesserts(MenuItem newItem);

    Optional<MenuItem> updateDrinkByName(String name, MenuItem newItem);

    Optional<MenuItem> updateFoodByName(String name, MenuItem newItem);

    Optional<MenuItem> updateDessertByName(String name, MenuItem newItem);

    Optional<MenuItem> findMenuItemByName(String name);

}
