package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.TotalIncomeDTO;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.Order;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/29/17.
 */

@Path("/report")
public class ReportResource {

    @EJB
    private OrderRepository orderRepo;

    @EJB
    private MenuItemRepository myMenu;

    @EJB
    private CustomerRepository customerRepo;

    @EJB
    private ReservationRepository resRepo;

    @EJB
    private ReportRepository reportRepository;

    @GET
    @Path("/reservation")
    public Response reservationReports
            (@QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        Date newStartDate = Date.valueOf(startDate);
        Date newEndDate = Date.valueOf(endDate);

        //TODO remove the try catch once the resRepo.getAllResBetweenDates gets implemented
        try {
            List<Reservation> allRes = resRepo.getAllResBetweenDates(newStartDate, newEndDate);

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderReport
            (@QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        Date newStartDate = Date.valueOf(startDate);
        Date newEndDate = Date.valueOf(endDate);

        List<Order> allOrders = orderRepo.getAllOrders();
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
                //TODO throw proper exceptions
            }
        }

        GenericEntity<List<Order>> resultWrapper = new GenericEntity<List<Order>>(result) {
        };

        return Response.ok(resultWrapper).build();
    }

    @GET
    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        List<Customer> customers = customerRepo.getAllCustomers();

        GenericEntity<List<Customer>> customerWrapper = new GenericEntity<List<Customer>>(customers) {
        };

        return Response.ok(customerWrapper).build();
    }

    @GET
    @Path("/sales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalSales
            (@QueryParam("startDate") final String startDate,
             @QueryParam("endDate") final String endDate) {

        return null;
    }

    @GET
    @Path("/totalIncome")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalIncome() {
        Optional<TotalIncomeDTO> opt = reportRepository.getTotalIncome();

        if (opt.isPresent()) {
            return Response.ok(opt.get()).build();
        }

        // TODO: throw proper exception
        return Response.ok().build();
    }
}
