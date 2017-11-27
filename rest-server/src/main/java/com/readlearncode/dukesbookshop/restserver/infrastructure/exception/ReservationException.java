package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 11/24/17.
 */
public class ReservationException extends Exception {
    private String message;

    public ReservationException() {
        this.message = "unfortunately we could't reserve the table right now!";
    }

    public ReservationException(String message) {
        this.message = message;
    }
}
