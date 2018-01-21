package com.readlearncode.dukesbookshop.restserver.rest;

import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories.ConcreteTableRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.TableCannotBeCreatedException;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.TableNotFoundException;
import org.jboss.logging.annotations.Pos;

import javax.ejb.EJB;
import javax.print.attribute.standard.Media;
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
    private ConcreteTableRepository tableRepo;

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

        Optional<Table> tableToUpdate = tableRepo.getTableById(table.getId());
        if (!tableToUpdate.isPresent()) {
            throw new TableNotFoundException("No Table Found To Update");
        }

        Optional<Table> updateTable = tableRepo.updateTable(tableToUpdate.get(), table);

        return Response.ok(updateTable.get()).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTable(final @Valid Table table) throws TableNotFoundException {

        Optional<Table> tableToUpdate = tableRepo.getTableById(table.getId());
        if (!tableToUpdate.isPresent()) {
            throw new TableNotFoundException("No Table Found To Delete");
        }

        tableRepo.deleteTableById(table.getId());

        return Response.ok("Done!").build();
    }

}
