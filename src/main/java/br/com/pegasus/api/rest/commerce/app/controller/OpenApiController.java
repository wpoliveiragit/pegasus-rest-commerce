package br.com.pegasus.api.rest.commerce.app.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

  private final ClassPathResource contractOpenAPI;

  public OpenApiController() {
    this.contractOpenAPI = new ClassPathResource("openapi.yaml");
  }

  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
  public ResponseEntity<ClassPathResource> getOpenApi() {
    return ResponseEntity.ok(contractOpenAPI);
  }

}
