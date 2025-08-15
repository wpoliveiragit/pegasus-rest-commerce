package br.com.pegasus.api.rest.commerce.app.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;

@Log4j2
@RestController
public class OpenApiInfoController {

    private byte[] openApi;

    public OpenApiInfoController(){
        try {
            openApi = Files.readAllBytes(new ClassPathResource("openapi.yaml").getFile().toPath());
            log.info("created openapi web contract");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping(value = "/openapi.yaml", produces = "application/yaml")
    public byte[] getContract() {
        return openApi;
    }


}
