package br.com.pegasus.api.rest.commerce.infra.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
