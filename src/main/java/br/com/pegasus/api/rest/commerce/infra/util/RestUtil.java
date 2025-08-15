package br.com.pegasus.api.rest.commerce.infra.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public final class RestUtil {

    public static <T> CompletableFuture<ResponseEntity<T>> createReponse(T response, HttpStatus status) {
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(response, status),
                Runnable::run
        );
    }

    public static <T> CompletableFuture<ResponseEntity<T>> createReponse(HttpStatus status) {
        return CompletableFuture.supplyAsync(
                () -> new ResponseEntity<>(status),
                Runnable::run
        );
    }

}
