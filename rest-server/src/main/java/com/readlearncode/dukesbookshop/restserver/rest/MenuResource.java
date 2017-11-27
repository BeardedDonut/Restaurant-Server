package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navid on 11/27/17.
 */
@Path("menu")
public class MenuResource {

    @EJB
    private Menu myRestaurantMenu;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {
        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDesserts());
        allItems.addAll(myRestaurantMenu.getDrinks());
        allItems.addAll(myRestaurantMenu.getFoods());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems){};
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/drinks")
    public Response getDrinksMenu() {
        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDrinks());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems){};
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/foods")
    public Response getFoodsMenu() {
        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getFoods());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems){};
        return Response.ok(allMenuItems).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/desserts")
    public Response getDessertsMenu() {
        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDesserts());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems){};
        return Response.ok(allMenuItems).build();
    }

    //TODO implement menu update api
}
