package br.com.pegasus.api.rest.commerce.app.handler;

import br.com.pegasus.api.rest.commerce.infra.data.ResponseExceptionData;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.AdviceLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.DateTimeUtil;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.UUID;

@AdviceLayerMarker
@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceHandler {

  private final HttpMethodHandler httpMethod;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> internalServerError(Exception ex, HttpServletRequest request) {
    return response(new ResponseExceptionData(HttpStatus.INTERNAL_SERVER_ERROR), request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseType> badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message = httpMethod.joinFieldErrors(ex.getBindingResult().getFieldErrors());
    return response(new ResponseExceptionData(HttpStatus.BAD_REQUEST, message), request);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ExceptionResponseType> appException(AppException ex, HttpServletRequest request) {
    return response(new ResponseExceptionData(ex.getHttpStatus(), ex.getMessage()), request);
  }

  private ResponseEntity<ExceptionResponseType> response(ResponseExceptionData respEx, HttpServletRequest request) {
    HttpStatus httpStatus = respEx.getHttpStatus();
    OffsetDateTime offsetDateTimeNow = DateTimeUtil.getOffsetDateTimeNow();
    ExceptionResponseType exResponse = new ExceptionResponseType();
    exResponse.setTraceId(UUID.fromString(request.getHeader(ConstUtil.REST_HEADER_X_TRACE_ID)));
    exResponse.setStatus(httpStatus.value());
    exResponse.setError(httpStatus.getReasonPhrase());
    exResponse.setTimestamp(offsetDateTimeNow);
    exResponse.setMessage(respEx.getMessage());
    exResponse.setPath(request.getRequestURI());
    return httpMethod.adviceResponse(httpStatus, exResponse);
  }

}