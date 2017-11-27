package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.MenuItem;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.OrderRepository;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/27/17.
 */
@Path("/orders")
public class OrderResource {

    @Context
    private UriInfo uriInfo;

    @EJB
    private OrderRepository orderRepo;

    @EJB
    private Menu myRestaurantMenu;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listOrders")
    public Response getAllOrders(){
        ArrayList<Order> allOrders = orderRepo.getAllOrders();
        GenericEntity<List<Order>> ordersWrapper = new GenericEntity<List<Order>>(allOrders){};
        return Response.ok(ordersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/menu")
    public Response getMenu() {
        ArrayList<MenuItem> allItems = new ArrayList<>();

        allItems.addAll(myRestaurantMenu.getDesserts());
        allItems.addAll(myRestaurantMenu.getDrinks());
        allItems.addAll(myRestaurantMenu.getFoods());

        GenericEntity<List<MenuItem>> allMenuItems = new GenericEntity<List<MenuItem>>(allItems){};
        return Response.ok(allMenuItems).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response order(@Valid final Order order) {
        //TODO check whether the order items are available

        if (order.getAccordingReservation() != null) {
            //TODO throw exception cause this order does not belong to anyone who reserved before
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Optional<Order> registeredOrder = orderRepo.saveOrderForReservation(order.getAccordingReservation(), order);
        if(registeredOrder.isPresent()) {
            return Response.ok(registeredOrder.get()).build();
        }
        //TODO throw exception cause we could'nt create the order
        return null;
    }
}
