package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions;

/**
 * Created by navid on 1/24/18.
 */
public class NoAvailableTableFoundException extends Exception {
    private String message;

    public NoAvailableTableFoundException() {
        this.message = "Couldn't find the desired table!";
    }

    public NoAvailableTableFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
