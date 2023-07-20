package ru.nikbekhter.vaadin.chat_vaadin.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
