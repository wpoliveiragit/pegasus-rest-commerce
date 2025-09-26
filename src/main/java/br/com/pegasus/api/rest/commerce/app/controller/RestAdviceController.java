package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.ConflictCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.CoreRuntimeException;
import br.com.pegasus.api.rest.commerce.infra.exception.InternalServerErrorCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.NotFoundCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.UnprocessableEntityCoreException;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import br.com.pegasus.gen.openapi.type.ExceptionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Log4j2
@RestControllerAdvice
public class RestAdviceController {

  @ExceptionHandler(InternalServerErrorCoreException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(InternalServerErrorCoreException ex) {
    log.warn("✪ Internal Server Error \n\t∴ Code: {} \n\t∴ Message: {}", ex.getCode(), ex.getMessage());
    return createResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotFoundCoreException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(NotFoundCoreException ex) {
    log.warn("✪ Not Found \n\t∴ Code: {} \n\t∴ Message: {}", ex.getCode(), ex.getMessage());
    return createResponse(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestCoreException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(BadRequestCoreException ex) {
    log.warn("✪ Bad Request \n\t∴ Code: {} \n\t∴ Message: {}", ex.getCode(), ex.getMessage());
    return createResponse(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConflictCoreException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(ConflictCoreException ex) {
    log.warn("✪ Conflict \n\t∴ Code: {} \n\t∴ Message: {}", ex.getCode(), ex.getMessage());
    return createResponse(ex, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UnprocessableEntityCoreException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(UnprocessableEntityCoreException ex) {
    log.warn("✪ Unprocessable Entity \n\t∴ Code: {} \n\t∴ Message: {}", ex.getCode(), ex.getMessage());
    return createResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private ResponseEntity<ExceptionResponseType> createResponse(CoreRuntimeException ex, HttpStatus httpStatus) {
    List<ExceptionType> list = List.of(//
        ExceptionType.builder()//
            .id(ex.getCode())//
            .build()//
    );

    return new ResponseEntity<>(//
        ExceptionResponseType.builder()//
            .details(list)//
            .build(), //
        httpStatus);
  }

}
