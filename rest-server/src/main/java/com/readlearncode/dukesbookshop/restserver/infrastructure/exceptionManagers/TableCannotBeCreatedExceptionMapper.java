package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by navid on 1/21/18.
 */
public class TableCannotBeCreatedExceptionMapper implements ExceptionMapper {
    @Override
    public Response toResponse(Throwable throwable) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
