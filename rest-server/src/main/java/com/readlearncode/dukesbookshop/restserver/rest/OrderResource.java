package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.OrderNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.OrderRepository;
import com.sun.org.apache.xpath.internal.operations.Or;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listOrders")
    public Response getAllOrders() {
        ArrayList<Order> allOrders = orderRepo.getAllOrders();
        GenericEntity<List<Order>> ordersWrapper = new GenericEntity<List<Order>>(allOrders) {
        };
        return Response.ok(ordersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listOrders/{status}")
    public Response getListOfOrdersByStatus(@PathParam("status") final int status) {
        /*
            1- fetch the corresponding status.
            2- group by the given status.
            3- return the list.
        */

        Order.OrderStatus correspondingStatus = Order.OrderStatus.values()[status];
        ArrayList<Order> allOrders = orderRepo.getOrdersWithStatus(correspondingStatus);

        GenericEntity<List<Order>> ordersWrapper = new GenericEntity<List<Order>>(allOrders) {
        };
        return Response.ok(ordersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{orderId}")
    public Response getOrderById(@PathParam("orderId") final int orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepo.getByOrderId(orderId);

        if(order.isPresent()) {
            return Response.ok(order.get()).build();
        } else {
            throw new OrderNotFoundException();
        }
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
        if (registeredOrder.isPresent()) {
            return Response.ok(registeredOrder.get()).build();
        }
        //TODO throw exception cause we could'nt create the order
        return null;
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/status")
    public Response updateStatus(
            @QueryParam("orderId") final int orderId,
            @QueryParam("status") final int statusNumber) throws OrderNotFoundException {

        Optional<Order> order = orderRepo.getByOrderId(orderId);
        //TODO check if the status number is valid
        Order.OrderStatus updateToStatus = Order.OrderStatus.values()[statusNumber];

        if (order.isPresent()) {
            return Response.ok(orderRepo.changeOrderStatus(order.get(), updateToStatus).get()).build();
        } else {
            throw new OrderNotFoundException();
        }
    }

}
