package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.CheckRequest;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation.ReservationManager;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.TableRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.NoAvailableTableFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.ReservationException;
import com.sun.mail.iap.ResponseInputStream;
import netscape.javascript.JSObject;

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

    @EJB
    private TableRepository tableRepo;

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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("check")
    public Response checkIfPossibleToReserve(final @Valid CheckRequest request) throws NoAvailableTableFoundException {
        //TODO: customer validation of customer mentioned in request!

        /* getting a list of all tables which has the requested number of seats or more */
        List<Table> tables = tableRepo.getTableBySeatSorted(request.getNumberOfSeats());

        /* get the first table which is possible to reserve! */
        Table tbl = resManager.isAvailable(request.getDate(), tables, request.getTs());


        if (tbl != null) {
            return Response.ok(tbl).build();
        } else {
            throw new NoAvailableTableFoundException();
        }

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserve(@Valid final CheckRequest checkRequest) throws ReservationException {
        Reservation res = resManager.reserveResult(checkRequest);
        if (res != null) {
            return Response.ok(res).build();
        } else {
            throw new ReservationException();
        }
    }

}
