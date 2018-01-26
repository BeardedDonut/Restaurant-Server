package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 1/10/18.
 */
@Provider
public class MenuItemNotFoundExceptionMapper
        implements
        ExceptionMapper<MenuItemNotFoundException> {

    @Override
    public Response toResponse(MenuItemNotFoundException e) {

        return Response.
                status(Response.Status.NOT_FOUND).
                entity(e).
                header("X-Validation-Failure", e.getMessage()).
                build();
    }
}
