package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exception.MenuItemCannotBeAddedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by navid on 1/10/18.
 */
public class MenuItemCannotBeAddedExceptionManager implements ExceptionMapper<MenuItemCannotBeAddedException> {
    @Override
    public Response toResponse(MenuItemCannotBeAddedException e) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
