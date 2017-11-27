package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 11/25/17.
 */
public class CustomerNotFoundException extends Exception {
    private String message;

    public CustomerNotFoundException() {
        this.message = "Customer not found!";
    }

    public CustomerNotFoundException(String message) {
        this.message = message;
    }
}
