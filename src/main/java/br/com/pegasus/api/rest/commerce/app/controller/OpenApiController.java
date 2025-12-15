package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

  private ClassPathResource contractOpenAPI;

  @PostConstruct
  public void init() {
    this.contractOpenAPI = new ClassPathResource(ConstUtil.PATH_OPENAPI);
  }

  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
  public ResponseEntity<ClassPathResource> getOpenApi() {
    return ResponseEntity.ok(contractOpenAPI);
  }

}