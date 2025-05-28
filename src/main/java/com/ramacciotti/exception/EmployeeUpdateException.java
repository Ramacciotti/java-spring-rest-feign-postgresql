package com.ramacciotti.exception;

public class EmployeeUpdateException extends RuntimeException {
    public EmployeeUpdateException() {
        super("could_not_update_employee");
    }
    public EmployeeUpdateException(String message) {
        super(message);
    }
}
