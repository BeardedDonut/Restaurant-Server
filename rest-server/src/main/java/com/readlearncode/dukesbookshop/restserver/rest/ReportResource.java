package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories.CustomerRepositoryBean;
import com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories.ReservationRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.OrderRepository;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/29/17.
 */

@Path("/report")
@RolesAllowed({"ADMIN", "USER"})
public class ReportResource {

    @EJB
    private OrderRepository orderRepo;

    @EJB
    private Menu myMenu;

    @EJB
    private CustomerRepositoryBean customerRepo;

    @EJB
    private ReservationRepository resRepo;

    @RolesAllowed("Admin")
    @GET
    @Path("/reservation")
    public Response reservationReports(
            @QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        Date newStartDate = Date.valueOf(startDate);
        Date newEndDate = Date.valueOf(endDate);

        //TODO remove the try catch once the resRepo.getAllResBetweenDates gets implemented
        try {
            ArrayList<Reservation> allRes = resRepo.getAllResBetweenDates(newStartDate, newEndDate);

            GenericEntity<List<Reservation>> resultWrapper = new GenericEntity<List<Reservation>>(allRes) {
            };

            return Response.ok(resultWrapper).build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GET
    @Path("/orders")
    public Response orderReport(
            @QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        Date newStartDate = Date.valueOf(startDate);
        Date newEndDate = Date.valueOf(endDate);

        ArrayList<Order> allOrders = orderRepo.getAllOrders();
        ArrayList<Order> result = new ArrayList<>();

        for (Order order : allOrders) {
            Optional<Reservation> res = orderRepo.findReservationByOrder(order);

            if (res.isPresent()) {
                int startDifference = res.get().getReservationDate().compareTo(newStartDate);
                int endDifference = res.get().getReservationDate().compareTo(newEndDate);

                if (startDifference > 0 && endDifference < 0) {
                    result.add(order);
                }
            } else {
                System.out.println("this order does not match any reservation!");
                //TODO throw proper exception
            }
        }

        GenericEntity<List<Order>> resultWrapper = new GenericEntity<List<Order>>(result) {
        };

        return Response.ok(resultWrapper).build();
    }

    @GET
    @Path("/customers")
    public Response customerReport() {
        List<Customer> customers = customerRepo.getAllCustomers();

        GenericEntity<List<Customer>> customerWrapper = new GenericEntity<List<Customer>>(customers) {
        };

        return Response.ok(customerWrapper).build();
    }

}
