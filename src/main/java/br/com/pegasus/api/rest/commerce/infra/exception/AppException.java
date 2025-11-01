package br.com.pegasus.api.rest.commerce.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

  private final String messageDetail;
  private final HttpStatus httpStatus;

  public AppException(HttpStatus httpStatus,  String messageDetail) {
    this.httpStatus = httpStatus;
    this.messageDetail = messageDetail;
  }

}
