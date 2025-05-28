package com.ramacciotti.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException() {
        super("could_not_fetch_data_from_external_api");
    }

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
