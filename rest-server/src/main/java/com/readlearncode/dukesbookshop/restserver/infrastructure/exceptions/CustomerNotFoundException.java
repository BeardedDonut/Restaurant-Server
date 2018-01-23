package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions;

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

    @Override
    public String getMessage(){
        return this.message;
    }
}
