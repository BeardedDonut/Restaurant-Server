package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.OrderRequestDTO;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.OrderStatus;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.OrderNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.OrderRepository;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

        List<Order> allOrders = orderRepo.getAllOrders();
        GenericEntity<List<Order>> ordersWrapper = new GenericEntity<List<Order>>(allOrders) {
        };

        return Response.ok(ordersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listOrders/{status}")
    public Response getListOfOrdersByStatus
            (@PathParam("status") final int status) {
        /*
            1- fetch the corresponding status.
            2- group by the given status.
            3- return the list.
        */

        OrderStatus correspondingStatus = OrderStatus.values()[status];
        List<Order> allOrders = orderRepo.getOrdersWithStatus(correspondingStatus);

        GenericEntity<List<Order>> ordersWrapper = new GenericEntity<List<Order>>(allOrders) {
        };
        return Response.ok(ordersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{orderId}")
    public Response getOrderById
            (@PathParam("orderId") final int orderId)
            throws
            OrderNotFoundException {

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
    public Response order
            (final OrderRequestDTO order) throws MenuItemNotFoundException {

        System.out.println(order);
        Optional<Order> createdOrder = orderRepo.processOrderRequest(order);

        if (createdOrder.isPresent() && createdOrder.get().getId() > 0) {
            order.setOrderId(createdOrder.get().getId());

            return Response.ok(order).build();
        }
        // TODO: return false or throw an exception

        //TODO check whether the order items are available
//
//        if (order.getAccordingReservation() != null) {
//            //TODO throw exceptions cause this order does not belong to anyone who reserved before
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        Optional<Order> registeredOrder = orderRepo.saveOrderForReservation(order.getAccordingReservation(), order);
//        if (registeredOrder.isPresent()) {
//            return Response.ok(registeredOrder.get()).build();
//        }
//        //TODO throw exceptions cause we could'nt create the order
        return Response.ok().build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/status")
    public Response updateStatus
            (@QueryParam("orderId") final int orderId,
            @QueryParam("status") final int statusNumber)
            throws
            OrderNotFoundException,
            MenuItemNotFoundException {

        Optional<Order> order = orderRepo.getByOrderId(orderId);
        //TODO check if the status number is valid
        OrderStatus updateToStatus = OrderStatus.values()[statusNumber];

        if (order.isPresent()) {
            return Response.ok(orderRepo.changeOrderStatus(order.get(), updateToStatus).get()).build();
        } else {
            throw new OrderNotFoundException();
        }
    }

}
