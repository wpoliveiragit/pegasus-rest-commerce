package br.com.pegasus.api.rest.commerce.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final String msg;

  public AppException(HttpStatus httpStatus, String msg) {
    super(msg);
    this.httpStatus = httpStatus;
    this.msg = msg;
  }
}

