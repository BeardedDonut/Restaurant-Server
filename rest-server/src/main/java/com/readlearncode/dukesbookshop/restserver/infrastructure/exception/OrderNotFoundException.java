package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 11/27/17.
 */
public class OrderNotFoundException extends Exception {
    private String message;

    public OrderNotFoundException() {
        this.message = "unfortunately the order object is not a valid order!";
    }

    public OrderNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
