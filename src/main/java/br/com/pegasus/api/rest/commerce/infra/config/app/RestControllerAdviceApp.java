package br.com.pegasus.api.rest.commerce.infra.config.app;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceApp {

  private static final AppBaseLog LOG = AppFactoryLog.getCommonLog(RestControllerAdviceApp.class);
  private static final String ERROR_MESSAGE = "[{}]: {} → {}";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(Exception ex) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    LOG.warn(ERROR_MESSAGE, httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());
    return createResponse(httpStatus, httpStatus.getReasonPhrase());
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(AppException ex) {
    HttpStatus httpStatus = ex.getHttpStatus();
    String messageDetail = ex.getMessageDetail();
    LOG.warn(ERROR_MESSAGE, httpStatus.value(), httpStatus.getReasonPhrase(), messageDetail);
    return createResponse(httpStatus, messageDetail);
  }

  public ResponseEntity<ExceptionResponseType> createResponse(HttpStatus httpStatus, String messageDetail) {
    ExceptionResponseType response = ExceptionResponseType.builder()//
        .code(httpStatus.value())//
        .message(httpStatus.getReasonPhrase())//
        .messageDetail(messageDetail)//
        .build();
    return new ResponseEntity<>(response, httpStatus);
  }

}