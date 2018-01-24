package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.TableCannotBeCreatedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 1/21/18.
 */
@Provider
public class TableCannotBeCreatedExceptionMapper implements ExceptionMapper<TableCannotBeCreatedException> {

    @Override
    public Response toResponse(TableCannotBeCreatedException e) {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(e).
                header("X-Validation-Failure", e.getMessage()).
                build();
    }
}
