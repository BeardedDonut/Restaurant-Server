package com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItemCategory;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemCannotBeAddedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */

@Stateless
public class MenuBean implements Menu {

    public Optional<MenuItem>
    addToMenuItem(MenuItem newItem, MenuItemCategory acceptedCategory)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        if (newItem.getCategory().equals(acceptedCategory)) {
            Session session = DatabaseConfig.createSessionFactory().openSession();

            session.beginTransaction();
            session.save(newItem);
            session.getTransaction().commit();
            System.out.println("New MenuItem Added; Assigned Id" + newItem.getId());

            session.close();
            return findMenuItemById(newItem.getId());
        } else {
            throw new MenuItemCannotBeAddedException("Category is not valid");
        }
    }

    public List<MenuItem>
    getMenuItemByCategory(MenuItemCategory givenCategory)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM menuItem WHERE menuItem.category = :givenCategory";
        Query query = session.createQuery(hql).setParameter("givenCategory", givenCategory);

        @SuppressWarnings("unchecked")
        List<MenuItem> menuItems = query.list();

        if (menuItems.size() == 0) {
            throw new MenuItemNotFoundException("No Menu Item Found With Specified Category");
        }

        session.close();
        return menuItems;
    }


    @Override
    public ArrayList<MenuItem> getDrinks() {
        //TODO
        return null;
    }

    @Override
    public ArrayList<MenuItem> getFoods() {
        //TODO
        return null;
    }

    @Override
    public ArrayList<MenuItem> getDesserts() {
        //TODO
        return null;
    }

    @Override
    public Optional<MenuItem> addToDrinks(MenuItem newItem)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        return addToMenuItem(newItem, MenuItemCategory.DRINK);
    }

    @Override
    public Optional<MenuItem> addToFoods(MenuItem newItem)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        return addToMenuItem(newItem, MenuItemCategory.FOOD);
    }

    @Override
    public Optional<MenuItem> addToDesserts(MenuItem newItem)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        return addToMenuItem(newItem, MenuItemCategory.DESSERT);
    }

    @Override
    public Optional<MenuItem> updateMenuItem(String name, MenuItem newItem) throws MenuItemNotFoundException {
        MenuItem retreviedMenuItem = findMenuItemByName(name).get();

        retreviedMenuItem.setCategory(newItem.getCategory());
        retreviedMenuItem.setName(newItem.getName());
        retreviedMenuItem.setPrice(newItem.getPrice());
        retreviedMenuItem.setImageFileName(newItem.getImageFileName());

        return Optional.of(retreviedMenuItem);

    }

    @Override
    public Optional<MenuItem> findMenuItemByName(String name) throws MenuItemNotFoundException {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String query = "FROM menuItem WHERE menuItem.name = :menuItemName";

        @SuppressWarnings("unchecked")
        List<MenuItem> items = session.createQuery(query).setParameter("menuItemName", name).list();
        System.out.println("Found " + items.size() + " menuItems");

        session.close();
        if (items.size() > 0) {
            return Optional.ofNullable(items.get(0));
        }

        throw new MenuItemNotFoundException("Menu Item With The Given Name Not Found!");
    }

    @Override
    public Optional<MenuItem> findMenuItemById(int id) throws MenuItemNotFoundException {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        MenuItem cs = (MenuItem) session.load(MenuItem.class, id);

        session.close();
        if (cs == null) {
            throw new MenuItemNotFoundException("Menu Item With The Given ID Not Found!");
        }

        return Optional.ofNullable(cs);
    }

}
