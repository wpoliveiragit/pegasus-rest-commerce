package br.com.pegasus.api.rest.commerce.infra.config.app;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceApp {

  private static final String HEADER_X_TRACE_ID = "X-Trace-Id";

  private final HttpMethodApp httpMethodApp;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(Exception ex, HttpServletRequest request) {
    return response(//
        HttpStatus.INTERNAL_SERVER_ERROR,//
        request.getHeader(HEADER_X_TRACE_ID),//
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),//
        request.getRequestURI(),//
        ex.getMessage()//
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(MethodArgumentNotValidException ex, HttpServletRequest request) {
    String details = ex.getBindingResult()//
        .getFieldErrors()//
        .stream()//
        .map(error -> error.getField() + ": " + error.getDefaultMessage())//
        .collect(Collectors.joining("; "));
    return response(HttpStatus.BAD_REQUEST, request.getHeader(HEADER_X_TRACE_ID), details, request.getRequestURI(), ex.getMessage()//
    );
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ExceptionResponseType> handlesErro(AppException ex, HttpServletRequest request) {
    return response(ex.getHttpStatus(), request.getHeader(HEADER_X_TRACE_ID), ex.getMsg(), request.getRequestURI(), ex.getMessage());
  }

  public ResponseEntity<ExceptionResponseType> response(HttpStatus httpStatus, String traceId, String messageResp, String path, String messageLog) {
    ExceptionResponseType resp = ExceptionResponseType.builder()//
        .traceId(UUID.fromString(traceId)) //
        .status(httpStatus.value()) //
        .error(httpStatus.getReasonPhrase()) //
        .timestamp(OffsetDateTime.now(ZoneOffset.UTC)) //
        .message(messageResp) //
        .path(path) //
        .build();
    return httpMethodApp.adviceResponse(httpStatus, resp);
  }

}