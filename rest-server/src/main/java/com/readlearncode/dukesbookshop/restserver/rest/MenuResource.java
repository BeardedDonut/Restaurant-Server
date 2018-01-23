package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.MenuItemCategory;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.MenuItemRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemCannotBeAddedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/27/17.
 */
@Path("menu")
public class MenuResource {

    @EJB
    private MenuItemRepository myRestaurantMenu;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {

        ArrayList<MenuItem> allItems = new ArrayList<>();

        List<MenuItem> drinks = myRestaurantMenu.getMenuItemByCategory(MenuItemCategory.DRINK);
        List<MenuItem> foods = myRestaurantMenu.getMenuItemByCategory(MenuItemCategory.FOOD);
        List<MenuItem> desserts = myRestaurantMenu.getMenuItemByCategory(MenuItemCategory.DESSERT);


        if (drinks != null) {
            allItems.addAll(drinks);
        }

        if (foods != null) {
            allItems.addAll(foods);
        }

        if (desserts != null) {
            allItems.addAll(desserts);
        }

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems) {
        };
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/drinks")
    public Response getDrinksMenu()
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDrinks());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems) {
        };
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/foods")
    public Response getFoodsMenu()
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getFoods());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems) {
        };
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/desserts")
    public Response getDessertsMenu()
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDesserts());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems) {
        };
        return Response.ok(allMenuItems).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewMenuItem(@Valid final MenuItem newMenuItem)
            throws MenuItemCannotBeAddedException {

        Optional<MenuItem> x = myRestaurantMenu.createNewMenuItem(newMenuItem);

        if (x.isPresent()) {
            return Response.ok(x.get()).build();
        }

        throw new MenuItemCannotBeAddedException("Cannot create This MenuItemRepository Item!");
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@Valid final MenuItem newMenuItem)
            throws MenuItemCannotBeAddedException, MenuItemNotFoundException {

        Optional<MenuItem> x = myRestaurantMenu.updateMenuItem(newMenuItem.getName(), newMenuItem);

        if (x.isPresent()) {
            return Response.ok(x.get()).build();
        }

        throw new MenuItemCannotBeAddedException("Cannot Update This MenuItemRepository Item!");
    }

    //TODO add delete api!
}
