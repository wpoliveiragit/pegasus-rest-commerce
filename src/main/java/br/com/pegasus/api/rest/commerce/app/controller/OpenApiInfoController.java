package br.com.pegasus.api.rest.commerce.app.controller;

import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Log4j2
@RestController
public class OpenApiInfoController {

//  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
//  public ClassPathResource openapi() {
//    return new ClassPathResource("openapi.yaml");
//  }

  private final byte[] openApi;

  public  OpenApiInfoController() {
    try {
      String html = MethodUtil.loadResourceFile(ConstUtil.FILE_OPENAPI);
      openApi = html.getBytes(StandardCharsets.UTF_8);
      log.info(ConstUtil.T_OPENAPI_INFO_CONTROLLER_INIT_SUCCESS);
    } catch (Exception ex) {
      log.warn(ConstUtil.T_OPENAPI_INFO_CONTROLLER_INIT_FAIL, ex.getMessage());
      throw new RuntimeException(ex);
    }
  }

  @LogAnnot
  @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
  public byte[] getContract() {
    return openApi;
  }

}
