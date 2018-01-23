package com.readlearncode.dukesbookshop.restserver.infrastructure.exception;

/**
 * Created by navid on 1/10/18.
 */
public class MenuItemCannotBeAddedException extends Exception {
    private String message;

    public MenuItemCannotBeAddedException() {
        this.message = "Cannot Add The MenuItemRepository Item!";
    }

    public MenuItemCannotBeAddedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
