package br.com.pegasus.api.rest.commerce.infra.handler;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;
import br.com.pegasus.gen.openapi.type.ExceptionResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestCoreException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(BadRequestCoreException ex) {
//        Map<String, Object> errors = new HashMap<>();
//        errors.put("", ex.getMessage())
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(null);
  }

  // Exceções genéricas
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    Map<String, Object> error = new HashMap<>();
    var response = new ExceptionResponseType.Builder().build();
    error.put("error", "Erro interno no servidor");
    error.put("details", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  // Exemplo para recurso não encontrado
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(IllegalArgumentException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("error", "Recurso não encontrado");
    error.put("details", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}

