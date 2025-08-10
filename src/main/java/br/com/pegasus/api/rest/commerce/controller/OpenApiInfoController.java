package br.com.pegasus.api.rest.commerce.controller;

import br.com.pegasus.api.rest.commerce.util.ConstUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;

@Log4j2
@RestController
public class OpenApiInfoController {

    private byte[] openApi;

    @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
    public byte[] getContract() {
        if (openApi == null) {
            createContract();
        }
        return openApi;
    }

    private void createContract() {
        try {
            openApi = Files.readAllBytes(new ClassPathResource(ConstUtil.OPEN_API_NAME_FILE).getFile().toPath());
            log.info(ConstUtil.OPEN_API_LOG_CREATE_CONTRACT);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
