package com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItemCategory;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemCannotBeAddedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */
public interface Menu {

    List<MenuItem> getMenuItemByCategory(MenuItemCategory givenCategory);

    List<MenuItem> getDrinks() throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    List<MenuItem> getFoods() throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    List<MenuItem> getDesserts() throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> addToDrinks(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> addToFoods(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> addToDesserts(MenuItem newItem) throws MenuItemCannotBeAddedException, MenuItemNotFoundException;

    Optional<MenuItem> findMenuItemByName(String name) throws MenuItemNotFoundException;

    Optional<MenuItem> findMenuItemById(int id) throws MenuItemNotFoundException;

    Optional<MenuItem> createNewMenuItem(MenuItem newItem);

    Optional<MenuItem> updateMenuItem(String name, MenuItem newItem) throws MenuItemNotFoundException;


}
