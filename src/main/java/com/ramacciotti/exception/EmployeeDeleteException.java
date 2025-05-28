package com.ramacciotti.exception;

public class EmployeeDeleteException extends RuntimeException {
    public EmployeeDeleteException() {
        super("could_not_delete_employee");
    }
    public EmployeeDeleteException(String message) {
        super(message);
    }
}
