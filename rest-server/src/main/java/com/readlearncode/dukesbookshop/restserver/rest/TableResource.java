package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories.TableRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.TableNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/25/17.
 */
@Path("/tables")
public class TableResource {
    @Context
    private UriInfo uriInfo;

    @EJB
    private TableRepository tableRepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTables() {
        List<Table> allTables = tableRepo.getAllTables();
        GenericEntity<List<Table>> tablesWrapper = new GenericEntity<List<Table>>(allTables) {
        };
        return Response.ok(tablesWrapper).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{tableId}")
    public Response getTableById(final @PathParam("tableId") int tableId)
            throws TableNotFoundException {

        Optional<Table> table = tableRepo.getTableById(tableId);

        if (table.isPresent()) {
            return Response.ok(table.get()).build();
        }

        throw new TableNotFoundException();
    }

}
