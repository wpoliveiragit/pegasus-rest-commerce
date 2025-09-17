package br.com.pegasus.api.rest.commerce.app.tool;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public final class ResponseTool {

  // Delegate / Controller
  public static <T> CompletableFuture<ResponseEntity<T>> ok(T response) {
    return createReponse(response, HttpStatus.OK);
  }

  public static <T> CompletableFuture<ResponseEntity<T>> created(T response) {
    return createReponse(response, HttpStatus.CREATED);
  }

  public static CompletableFuture<ResponseEntity<Void>> noContent() {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT), Runnable::run);
  }

  // Advice
  public static <T> CompletableFuture<ResponseEntity<T>> internalServerError(T response) {
    return createReponse(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static <T> CompletableFuture<ResponseEntity<T>> badRequest(T response) {
    return createReponse(response, HttpStatus.BAD_REQUEST);
  }

  public static <T> CompletableFuture<ResponseEntity<T>> conflict(T response) {
    return createReponse(response, HttpStatus.CONFLICT);
  }

  public static <T> CompletableFuture<ResponseEntity<T>> notFound(T response) {
    return createReponse(response, HttpStatus.NOT_FOUND);
  }

  public static <T> CompletableFuture<ResponseEntity<T>> unprocessableEntity(T response) {
    return createReponse(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  // Create Response
  private static <T> CompletableFuture<ResponseEntity<T>> createReponse(T response, HttpStatus status) {
    return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(response, status), Runnable::run);
  }

}
