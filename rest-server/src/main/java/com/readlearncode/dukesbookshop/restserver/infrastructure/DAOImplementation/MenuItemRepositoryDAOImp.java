package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItemCategory;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.MenuItemRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemCannotBeAddedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/26/17.
 */

@Stateless(name = "MenuItemRepositoryDAOImp")
public class MenuItemRepositoryDAOImp implements MenuItemRepository {

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

    @Override
    public List<MenuItem>
    getMenuItemByCategory(MenuItemCategory givenCategory) {

        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM menuItem WHERE category = :givenCategory";
        Query query = session.createQuery(hql).setParameter("givenCategory", givenCategory);

        @SuppressWarnings("unchecked")
        List<MenuItem> menuItems = query.list();

        if (menuItems.size() == 0) {
            return null;
        }

        session.close();
        return menuItems;
    }


    @Override
    public List<MenuItem> getDrinks() throws MenuItemCannotBeAddedException, MenuItemNotFoundException {
        List<MenuItem> x = getMenuItemByCategory(MenuItemCategory.DRINK);

        if (x == null) {
            throw new MenuItemNotFoundException("No Drink Is Found!");
        }

        return x;
    }

    @Override
    public List<MenuItem> getFoods() throws MenuItemCannotBeAddedException, MenuItemNotFoundException {
        List<MenuItem> x = getMenuItemByCategory(MenuItemCategory.FOOD);

        if (x == null) {
            throw new MenuItemNotFoundException("No Food Is Found!");
        }

        return x;
    }

    @Override
    public List<MenuItem> getDesserts() throws MenuItemCannotBeAddedException, MenuItemNotFoundException {
        List<MenuItem> x = getMenuItemByCategory(MenuItemCategory.DESSERT);

        if (x == null) {
            throw new MenuItemNotFoundException("No Dessert Is Found!");
        }

        return x;
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
        MenuItem retrievedMenuItem = findMenuItemByName(name).get();

        //TODO: fix updating -> check out table update process!
        retrievedMenuItem.setCategory(newItem.getCategory());
        retrievedMenuItem.setName(newItem.getName());
        retrievedMenuItem.setPrice(newItem.getPrice());
        retrievedMenuItem.setImageFileName(newItem.getImageFileName());

        return Optional.of(retrievedMenuItem);
    }

    @Override
    public Optional<MenuItem> findMenuItemByName(String name) throws MenuItemNotFoundException {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String query = "FROM menuItem WHERE name = :menuItemName";

        @SuppressWarnings("unchecked")
        List<MenuItem> items = session.createQuery(query).setParameter("menuItemName", name).list();
        System.out.println("Found " + items.size() + " menuItems");

        session.close();
        if (items.size() > 0) {
            return Optional.ofNullable(items.get(0));
        }

        throw new MenuItemNotFoundException("MenuItemRepository Item With The Given Name Not Found!");
    }

    @Override
    public Optional<MenuItem> findMenuItemById(int id) throws MenuItemNotFoundException {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        MenuItem cs = (MenuItem) session.load(MenuItem.class, id);

        session.close();
        if (cs == null) {
            throw new MenuItemNotFoundException("MenuItemRepository Item With The Given ID Not Found!");
        }

        return Optional.ofNullable(cs);
    }

    @Override
    public Optional<MenuItem> createNewMenuItem(final MenuItem newItem) {
        MenuItem mi = new MenuItem();
        mi.setImageFileName(newItem.getImageFileName());
        mi.setCategory(newItem.getCategory());
        mi.setPrice(newItem.getPrice());
        mi.setName(newItem.getName());

        Session session = DatabaseConfig.createSessionFactory().openSession();

        session.beginTransaction();
        session.save(mi);
        session.getTransaction().commit();
        System.out.println("MenuItem Created:" + mi.toString());

        session.close();
        return Optional.ofNullable(mi);
    }

}
