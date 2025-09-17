package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Log4j2
@RestController
public class OpenApiInfoController {

  private final byte[] openApi;

  public OpenApiInfoController() {
    try {
      String html = Files.readString(new ClassPathResource("openapi.yaml").getFile().toPath());
      openApi = html.getBytes(StandardCharsets.UTF_8);
      log.info("created openapi web contract");
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @LogAnnot
  @GetMapping(value = "${springdoc.swagger-ui.url}", produces = "application/yaml")
  public byte[] getContract() {
    return openApi;
  }

}
