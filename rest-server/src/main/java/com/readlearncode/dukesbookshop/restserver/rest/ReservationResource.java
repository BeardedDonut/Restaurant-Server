package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.CheckRequestDTO;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation.ReservationManager;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.CustomerRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.TableRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.NoAvailableTableFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.ReservationException;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

    @EJB
    private CustomerRepository customerRepo;

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
    @Path("/check")
    public Response checkIfPossibleToReserve(final @Valid CheckRequestDTO checkRequestDTO)
            throws
            NoAvailableTableFoundException,
            ReservationException {

        /* Check if Customer is valid */
        Optional<Customer> cs = customerRepo.getCustomerById(checkRequestDTO.getRelatedCustomer().getCustomerId());

        if (!cs.isPresent()) {
            throw new ReservationException("Customer Is Not Valid");
        }
        checkRequestDTO.setRelatedCustomer(cs.get());

        /* getting a list of all tables which has the requested number of seats or more */
        List<Table> tables = tableRepo.getTableBySeatSorted(checkRequestDTO.getNumberOfSeats());

        /* get the first table which is possible to reserve! */
        Table tbl = resManager.isAvailable(checkRequestDTO.getDate(), tables, checkRequestDTO.getTs());

        if (tbl != null) {
            return Response.ok(tbl).build();
        } else {
            throw new NoAvailableTableFoundException();
        }

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserve(@Valid final CheckRequestDTO checkRequestDTO)
            throws
            ReservationException {

        /* Check if Customer is valid */
        Optional<Customer> cs = customerRepo.getCustomerById(checkRequestDTO.getRelatedCustomer().getCustomerId());

        if (!cs.isPresent()) {
            throw new ReservationException("Customer Is Not Valid");
        }
        checkRequestDTO.setRelatedCustomer(cs.get());


        /* Reserve if possible */
        Reservation res = resManager.reserveResult(checkRequestDTO);

        if (res != null) {
            return Response.ok(res).build();
        } else {
            throw new ReservationException();
        }
    }

}
