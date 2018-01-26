package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 1/26/18.
 */
@Provider
public class DefaultExceptionMapper
        implements
        ExceptionMapper<Exception> {

    @Override
    public Response toResponse
            (Exception exception) {

        exception.printStackTrace();
        return Response.
                serverError().
                entity(exception.getMessage()).
                build();
    }
}
