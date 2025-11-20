package br.com.pegasus.api.rest.commerce.app.handler;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public final class HttpMethodHandler {

  public <T> CompletableFuture<ResponseEntity<T>> ok(T response) {
    return createReponse(HttpStatus.OK, response);
  }

  public <T> CompletableFuture<ResponseEntity<T>> created(T response) {
    return createReponse(HttpStatus.CREATED, response);
  }

  public CompletableFuture<ResponseEntity<Void>> noContent() {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT), Runnable::run);
  }

  public<T> ResponseEntity<T> adviceResponse(HttpStatus httpStatus, T response){
    return new ResponseEntity<>(response, httpStatus);
  }

  public String joinFieldErrors(List<FieldError> list) {
    return list.stream()//
        .map(error -> error.getField() + ConstUtil.KEY_VALUE_SEP + error.getDefaultMessage())//
        .collect(Collectors.joining(ConstUtil.ENTRY_SEP));
  }

  private <T> CompletableFuture<ResponseEntity<T>> createReponse(HttpStatus status, T response) {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, status), Runnable::run);
  }

}
