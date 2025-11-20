package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ResponseExceptionRecord {
  private final HttpStatus httpStatus;
  private final String message;
}
