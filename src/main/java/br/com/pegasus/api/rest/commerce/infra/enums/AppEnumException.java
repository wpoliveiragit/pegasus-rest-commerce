package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AppEnumException {

  CONFLICT_NAME(HttpStatus.CONFLICT, "Name already registered"),//
  CONFLICT_DOCUMENT_NUMBER(HttpStatus.CONFLICT, "CPF already registered"),//

  NOT_FOUND_ELEMENT(HttpStatus.CONFLICT, "Element not found"),//
  NOT_FOUND_COOPERATOR(HttpStatus.CONFLICT, "Cooperator not found"),//
  NOT_FOUND_PRODUCT(HttpStatus.CONFLICT, "Product not found"),//
  NOT_FOUND_TAX_RECEIPT(HttpStatus.CONFLICT, "Tax Receipt not found"),

  BAD_REQUEST_PAGE(HttpStatus.BAD_REQUEST, "Invalid Page"),//
  BAD_REQUEST_SIZE(HttpStatus.BAD_REQUEST, "Invalid Size"),//
  BAD_REQUEST_ID(HttpStatus.BAD_REQUEST, "Invalid ID"),//
  BAD_REQUEST_TAX_RECEIPT_ID(HttpStatus.BAD_REQUEST, "Invalid Tax Receipt ID"),//
  BAD_REQUEST_PRODUCT_ID(HttpStatus.BAD_REQUEST, "Invalid Product ID"),//
  BAD_REQUEST_COOPERATOR_ID(HttpStatus.BAD_REQUEST, "Invalid Cooperator ID"),//
  BAD_REQUEST_PRICE(HttpStatus.BAD_REQUEST, "Invalid Price"),//
  BAD_REQUEST_QUANTITY(HttpStatus.BAD_REQUEST, "Invalid Quantity"),//
  BAD_REQUEST_BODY(HttpStatus.BAD_REQUEST, "Invalid Body"),//
  BAD_REQUEST_NAME(HttpStatus.BAD_REQUEST, "Invalid Name"),//
  BAD_REQUEST_DOCUMENT_NUMBER(HttpStatus.BAD_REQUEST, "Invalid Document Number"),//

  INTERNAL_SERVER_ERROR_DB_ERROR_QUERY(HttpStatus.INTERNAL_SERVER_ERROR, "problems querying the database"),//
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

  private final AppException appException;

  AppEnumException(HttpStatus httpStatus, String message) {
    this.appException = new AppException(httpStatus, message);
  }

}
