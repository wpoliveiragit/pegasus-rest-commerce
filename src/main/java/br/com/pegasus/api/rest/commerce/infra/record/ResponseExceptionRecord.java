package br.com.pegasus.api.rest.commerce.infra.record;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseExceptionRecord(HttpStatus httpStatus, String message) {
}
