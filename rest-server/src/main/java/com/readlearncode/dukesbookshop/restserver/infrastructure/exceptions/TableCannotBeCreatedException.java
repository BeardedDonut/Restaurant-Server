package com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions;

/**
 * Created by navid on 1/21/18.
 */
public class TableCannotBeCreatedException extends Exception {
    private String message;

    public TableCannotBeCreatedException() {
        this.message = "table cannot be created!";
    }

    public TableCannotBeCreatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
