package com.ramacciotti.exception;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException() {
        super("could_not_access_database");
    }

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
