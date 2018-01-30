package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.ReservationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 11/24/17.
 */
@Provider
public class ReservationExceptionMapper
        implements
        ExceptionMapper<ReservationException> {

    @Override
    public Response toResponse(ReservationException e) {

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(e).
                header("X-Failure", e.getMessage()).
                build();
    }
}
