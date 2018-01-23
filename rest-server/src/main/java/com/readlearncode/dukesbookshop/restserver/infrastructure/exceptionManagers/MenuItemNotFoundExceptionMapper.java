package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.MenuItemNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by navid on 1/10/18.
 */
public class MenuItemNotFoundExceptionMapper implements ExceptionMapper<MenuItemNotFoundException> {

    @Override
    public Response toResponse(MenuItemNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
