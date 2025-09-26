package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Log4j2
@RestController
public class OpenApiInfoController {

  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
  public ResponseEntity<ClassPathResource> getOpenApi() {

    ClassPathResource resource = new ClassPathResource(ConstUtil.FILE_OPENAPI);
    return ResponseEntity.ok(resource);
  }

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public String getHtml() throws Exception {
    ClassPathResource resource = new ClassPathResource("site/index.html");
    return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
  }


//  private final byte[] openApi;
//
//  public OpenApiInfoController() {
//    try {
//      String html = MethodUtil.loadResourceFile(ConstUtil.FILE_OPENAPI);
//      openApi = html.getBytes(StandardCharsets.UTF_8);
//      log.info(ConstUtil.T_OPENAPI_INFO_CONTROLLER_INIT_SUCCESS);
//    } catch (Exception ex) {
//      log.warn(ConstUtil.T_OPENAPI_INFO_CONTROLLER_INIT_FAIL, ex.getMessage());
//      throw new RuntimeException(ex);
//    }
//  }
//
//  @LogAnnot
//  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
//  public byte[] getContract() {
//    return openApi;
//  }

}
