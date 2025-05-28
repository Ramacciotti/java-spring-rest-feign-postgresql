package com.ramacciotti.exception;

public class DeleteEmployeeException extends RuntimeException {
    public DeleteEmployeeException() {
        super("could_not_delete_employee");
    }

    public DeleteEmployeeException(String message) {
        super(message);
    }

    public DeleteEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}