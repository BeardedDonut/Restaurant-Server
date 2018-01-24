package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemCannotBeAddedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 1/10/18.
 */
@Provider
public class MenuItemCannotBeAddedExceptionManager implements ExceptionMapper<MenuItemCannotBeAddedException> {
    @Override
    public Response toResponse(MenuItemCannotBeAddedException e) {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(e).
                header("X-Validation-Failure", e.getMessage()).
                build();
    }
}
