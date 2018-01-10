package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 1/10/18.
 */
public class MenuItemNotFoundException extends Exception {
    private String message;

    public MenuItemNotFoundException() {
        this.message = "Menu Item Not Found!";
    }

    public MenuItemNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
