package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 11/25/17.
 */
public class CustomerAlreadySigned extends Exception {
    private String message;

    public CustomerAlreadySigned() {
        this.message = "Someone has signed with this phone number before!";
    }

    public CustomerAlreadySigned(String message) {
        this.message = message;
    }
}
