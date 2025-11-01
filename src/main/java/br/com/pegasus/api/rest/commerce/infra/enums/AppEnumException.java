package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AppEnumException {

  CONFLICT_NAME(HttpStatus.CONFLICT, "Name already registered"), //

  NOT_FOUND_ELEMENT(HttpStatus.NOT_FOUND, "Element not found"), //

  BAD_REQUEST_PAGE(HttpStatus.BAD_REQUEST, "Invalid Page"), //
  BAD_REQUEST_SIZE(HttpStatus.BAD_REQUEST, "Invalid Size"), //
  BAD_REQUEST_BODY(HttpStatus.BAD_REQUEST, "Invalid Body"), //
  BAD_REQUEST_ID(HttpStatus.BAD_REQUEST, "Invalid ID"), //
  BAD_REQUEST_NAME(HttpStatus.BAD_REQUEST, "Invalid Name"), //
  BAD_REQUEST_PRICE(HttpStatus.BAD_REQUEST, "Invalid Price"), //
  BAD_REQUEST_QUANTITY(HttpStatus.BAD_REQUEST, "Invalid Quantity"); //

  private final AppException appException;

  AppEnumException(HttpStatus httpStatus, String message) {
    this.appException = new AppException(httpStatus, message);
  }

}
