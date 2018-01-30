package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions;

/**
 * Created by navid on 11/25/17.
 */
public class CustomerAlreadySignedException
        extends
        Exception {

    private String message;

    public CustomerAlreadySignedException() {
        this.message = "Someone has signed with this phone number before!";
    }

    public CustomerAlreadySignedException
            (String message) {

        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
