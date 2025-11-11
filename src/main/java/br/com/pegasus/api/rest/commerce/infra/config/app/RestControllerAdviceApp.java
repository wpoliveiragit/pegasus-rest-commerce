package br.com.pegasus.api.rest.commerce.infra.config.app;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.record.ResponseExceptionRecord;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceApp {

  private final HttpMethodApp httpMethodApp;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> internalServerError(Exception ex, HttpServletRequest request) {
    return response(//
        ResponseExceptionRecord.builder()//
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)//
            .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())//
            .build(),//
        request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseType> badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
    return response(//
        ResponseExceptionRecord.builder()//
            .httpStatus(HttpStatus.BAD_REQUEST)//
            .message(httpMethodApp.joinFieldErrors(ex.getBindingResult().getFieldErrors()))//
            .build(),//
        request);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ExceptionResponseType> appException(AppException ex, HttpServletRequest request) {
    return response(//
        ResponseExceptionRecord.builder()//
            .httpStatus(ex.getHttpStatus())//
            .message(ex.getMsg()).build(),//
        request);
  }

  public ResponseEntity<ExceptionResponseType> response(ResponseExceptionRecord respEx, HttpServletRequest request) {
    HttpStatus httpStatus = respEx.httpStatus();
    ExceptionResponseType resp = ExceptionResponseType.builder()//
        .traceId(UUID.fromString(request.getHeader(ConstUtil.HEADER_X_TRACE_ID)))//
        .status(httpStatus.value())//
        .error(httpStatus.getReasonPhrase())//
        .timestamp(MethodUtil.Date.getOffsetDateTimeNow())//
        .message(respEx.message())//
        .path(request.getRequestURI())//
        .build();//
    return httpMethodApp.adviceResponse(httpStatus, resp);
  }

}