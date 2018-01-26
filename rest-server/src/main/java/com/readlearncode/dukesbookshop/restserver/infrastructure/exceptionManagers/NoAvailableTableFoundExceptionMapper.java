package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.NoAvailableTableFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 1/24/18.
 */
@Provider
public class NoAvailableTableFoundExceptionMapper
        implements
        ExceptionMapper<NoAvailableTableFoundException> {

    @Override
    public Response toResponse(NoAvailableTableFoundException e) {

        return Response.
                status(Response.Status.NOT_FOUND).
                entity(e).
                header("X-Validation-Failure", e.getMessage()).
                build();
    }
}
