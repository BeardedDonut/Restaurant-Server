package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions;

/**
 * Created by navid on 11/24/17.
 */
public class ReservationException
        extends
        Exception {

    private String message;

    public ReservationException() {
        this.message = "No Available Table For Requested Time Span";
    }

    public ReservationException
            (String message) {

        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
