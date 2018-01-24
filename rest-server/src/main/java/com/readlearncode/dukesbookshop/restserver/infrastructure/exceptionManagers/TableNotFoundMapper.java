package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.TableNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 11/25/17.
 */
@Provider
public class TableNotFoundMapper implements ExceptionMapper<TableNotFoundException> {

    @Override
    public Response toResponse(TableNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).
                entity(e).
                header("X-Validation-Failure", e.getMessage()).
                build();
    }
}
