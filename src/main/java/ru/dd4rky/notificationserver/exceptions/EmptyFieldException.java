package ru.dd4rky.notificationserver.exceptions;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException() {

    }
}
