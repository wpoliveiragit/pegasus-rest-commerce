package br.com.pegasus.api.rest.commerce.infra.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Log4j2
@Component
public class EndpointsPrinter implements CommandLineRunner {

    private final RequestMappingHandlerMapping handlerMapping;

    public EndpointsPrinter(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void run(String... args) {
        handlerMapping.getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) ->
                        log.info(requestMappingInfo));
    }
}

