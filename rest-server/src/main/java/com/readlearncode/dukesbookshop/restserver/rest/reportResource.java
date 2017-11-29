package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.infrastructure.ReservationRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.Menu;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.OrderRepository;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.xml.ws.Response;

/**
 * Created by navid on 11/29/17.
 */

@Path("/report")
public class reportResource {

    @EJB
    private OrderRepository orderRepo;

    @EJB
    private Menu myMenu;

    @EJB
    private ReservationRepository resRepo;

    @GET
    @Path("/reservation")
    public Response reservationReports(
            @QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        //TODO return list of reservations from the start date to end date
        return null;
    }

    @GET
    @Path("/orders")
    public Response orderReport(
            @QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate) {

        //TODO return list of reservations from the start date to end date
        return null;
    }

    @GET
    @Path("/customers")
    public Response customerReport() {
        //TODO return list of all customers
        return null;
    }

}
