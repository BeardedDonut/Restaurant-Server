package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Request;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.infrastructure.ReservationManager;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.ReservationException;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by navid on 11/24/17.
 */
@Path("/reserve")
public class ReservationResource {

    @Context
    private UriInfo uriInfo;

    @EJB
    private ReservationManager resManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{date}")
    public Response getAllReservations(final @PathParam("date") String date) {
        Date dt = Date.valueOf(date);
        List<Reservation> allRes = resManager.retrieveAllReservation(dt);

        GenericEntity<List<Reservation>> resWrapper = new GenericEntity<List<Reservation>>(allRes) {
        };

        return Response.ok(resWrapper).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserve(@Valid final Request request) throws ReservationException {
        Reservation res = resManager.reserveResult(request);
        if (res != null) {
            return Response.ok(res).build();
        } else {
            throw new ReservationException();
        }
    }

}
