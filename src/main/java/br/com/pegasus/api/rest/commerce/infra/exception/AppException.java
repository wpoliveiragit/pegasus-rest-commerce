package br.com.pegasus.api.rest.commerce.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
@RequiredArgsConstructor
public class AppException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final String msg;
}

