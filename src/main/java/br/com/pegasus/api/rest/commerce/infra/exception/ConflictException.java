package br.com.pegasus.api.rest.commerce.infra.exception;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
