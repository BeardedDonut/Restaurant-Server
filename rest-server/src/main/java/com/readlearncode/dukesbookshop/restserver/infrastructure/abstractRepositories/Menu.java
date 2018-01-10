package com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemCannotBeAddedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface Menu {

    ArrayList<MenuItem> getDrinks();

    ArrayList<MenuItem> getFoods();

    ArrayList<MenuItem> getDesserts();

    Optional<MenuItem> addToDrinks(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> addToFoods(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> addToDesserts(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> updateMenuItem(String name, MenuItem newItem) throws MenuItemNotFoundException;

    Optional<MenuItem> findMenuItemByName(String name) throws MenuItemNotFoundException;

    Optional<MenuItem> findMenuItemById(int id) throws MenuItemNotFoundException;

}
