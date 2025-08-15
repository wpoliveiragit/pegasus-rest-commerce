package br.com.pegasus.api.rest.commerce.infra.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
