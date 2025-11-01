package br.com.pegasus.api.rest.commerce.infra.config.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public final class MethodApp {

  public <T> CompletableFuture<ResponseEntity<T>> createReponse(HttpStatus status, T response) {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, status), Runnable::run);
  }

  public <T> CompletableFuture<ResponseEntity<T>> ok(T response) {
    return createReponse(HttpStatus.OK, response);
  }

  public <T> CompletableFuture<ResponseEntity<T>> created(T response) {
    return createReponse(HttpStatus.CREATED, response);
  }

  public CompletableFuture<ResponseEntity<Void>> noContent() {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT), Runnable::run);
  }

  public <T> ResponseEntity<T> conflict(T response) {
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

}
