package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.TableRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.TableCannotBeCreatedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.TableNotFoundException;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewTable(final @Valid Table table) throws TableCannotBeCreatedException {
        Optional<Table> createdTable = tableRepo.createTable(table);

        if (createdTable.isPresent()) {
            return Response.ok(createdTable.get()).build();
        }

        throw new TableCannotBeCreatedException();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTable(final @Valid Table table) throws TableNotFoundException {

        Optional<Table> updateTable = tableRepo.updateTable(table.getNumberOfSeats(), table.getId());

        return Response.ok(updateTable.get()).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // TODO: Test delete endpoint
    public Response deleteTable(final Table table) throws TableNotFoundException {

        Optional<Table> tableToUpdate = tableRepo.getTableById(table.getId());
        if (!tableToUpdate.isPresent()) {
            throw new TableNotFoundException("No Table Found To Delete");
        }

        tableRepo.deleteTableById(table.getId());

        return Response.ok(table).build();
    }

}
