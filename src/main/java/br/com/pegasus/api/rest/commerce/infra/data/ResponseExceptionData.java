package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ResponseExceptionData {

  private final HttpStatus httpStatus;
  private final String message;

  public ResponseExceptionData(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    this.message = httpStatus.getReasonPhrase();
  }

}