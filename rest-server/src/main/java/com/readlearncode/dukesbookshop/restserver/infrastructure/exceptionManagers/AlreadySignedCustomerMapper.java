package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptionManagers;

import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.CustomerAlreadySignedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by navid on 11/25/17.
 */
@Provider
public class AlreadySignedCustomerMapper
        implements
        ExceptionMapper<CustomerAlreadySignedException> {
    @Override
    public Response toResponse
            (CustomerAlreadySignedException customerAlreadySignedException) {

        return Response.status(Response.Status.CONFLICT).build();
    }
}
