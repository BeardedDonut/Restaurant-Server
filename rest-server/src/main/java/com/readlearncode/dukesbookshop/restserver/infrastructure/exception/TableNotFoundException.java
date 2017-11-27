package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 11/25/17.
 */
public class TableNotFoundException extends Exception {
    private String message;

    public TableNotFoundException() {
        this.message = "table not found!";
    }

    public TableNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
