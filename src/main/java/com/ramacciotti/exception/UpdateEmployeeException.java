package com.ramacciotti.exception;

public class UpdateEmployeeException extends RuntimeException {
    public UpdateEmployeeException() {
        super("could_not_update_employee");
    }

    public UpdateEmployeeException(String message) {
        super(message);
    }

    public UpdateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}