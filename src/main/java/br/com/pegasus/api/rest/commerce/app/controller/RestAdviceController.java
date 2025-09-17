package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.ConflictCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.CoreRuntimeException;
import br.com.pegasus.api.rest.commerce.infra.exception.InternalServerErrorCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.NotFoundCoreException;
import br.com.pegasus.api.rest.commerce.infra.exception.UnprocessableCoreException;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import br.com.pegasus.gen.openapi.type.ExceptionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@RestControllerAdvice
public class RestAdviceController {

  @LogAnnot
  @ExceptionHandler(InternalServerErrorCoreException.class)
  public CompletableFuture<ResponseEntity<ExceptionResponseType>> handlesErro(InternalServerErrorCoreException ex) {
    log.warn("InternalServerError: Code:{} Message{}", ex.getCode(), ex.getMessage());
    return ResponseTool.internalServerError(createResponse(ex));
  }

  @LogAnnot
  @ExceptionHandler(NotFoundCoreException.class)
  public CompletableFuture<ResponseEntity<ExceptionResponseType>> handlesErro(NotFoundCoreException ex) {
    log.warn("NotFound: Code:{} Message{}", ex.getCode(), ex.getMessage());
    return ResponseTool.notFound(createResponse(ex));
  }

  @LogAnnot
  @ExceptionHandler(BadRequestCoreException.class)
  public CompletableFuture<ResponseEntity<ExceptionResponseType>> handlesErro(BadRequestCoreException ex) {
    log.warn("BadRequest: Code:{} Message{}", ex.getCode(), ex.getMessage());
    return ResponseTool.badRequest(createResponse(ex));
  }

  @LogAnnot
  @ExceptionHandler(ConflictCoreException.class)
  public CompletableFuture<ResponseEntity<ExceptionResponseType>> handlesErro(ConflictCoreException ex) {
    log.warn("Conflict: Code:{} Message{}", ex.getCode(), ex.getMessage());
    return ResponseTool.conflict(createResponse(ex));
  }

  @LogAnnot
  @ExceptionHandler(UnprocessableCoreException.class)
  public CompletableFuture<ResponseEntity<ExceptionResponseType>> handlesErro(UnprocessableCoreException ex) {
    log.warn("UnprocessableEntity: Code:{} Message{}", ex.getCode(), ex.getMessage());
    return ResponseTool.unprocessableEntity(createResponse(ex));
  }

  private ExceptionResponseType createResponse(CoreRuntimeException exCore) {
    return ExceptionResponseType.builder().details(List.of(ExceptionType.builder().id(exCore.getCode()).build())).build();
  }

}
