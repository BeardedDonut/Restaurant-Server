package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.CustomerAlreadySigned;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.CustomerNotFoundException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.repositories.CustomerRepository;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/25/17.
 */

@Path("/customers")
public class CustomerResource {

    @Context
    private UriInfo uriInfo;

    @EJB
    private CustomerRepository customerRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() throws CustomerNotFoundException {
        List<Customer> customers = customerRepo.getAllCustomers();
        GenericEntity<List<Customer>> customersWrapper = new GenericEntity<List<Customer>>(customers) {
        };
        return Response.ok(customersWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{customerId}")
    public Response getCustomerById(final @PathParam("customerId") int id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepo.getCustomerById(id);

        if (customer.isPresent()) {
            return Response.ok(customer.get()).build();
        }

        throw new CustomerNotFoundException();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProfile(@Valid final Customer cs) throws CustomerAlreadySigned {

        if (customerRepo.getCustomerByTel(cs.getTelephoneNumber()).isPresent()) {
            throw new CustomerAlreadySigned();
        } else {
            Optional<Customer> regCs = customerRepo.createNewProfile(cs);
            return Response.ok(regCs.get()).build();
        }
    }

}
