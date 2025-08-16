package br.com.pegasus.api.rest.commerce.infra.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j2
@Component
public class EndpointsPrinter implements CommandLineRunner {

    private final String keyNotProd = "NOT-PROD";

    private final RequestMappingHandlerMapping handlerMapping;

    public EndpointsPrinter(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void run(String... args) {
        Map<String, List<String>> endpoints = new HashMap<>(1);
        endpoints.put(keyNotProd, new ArrayList<>());
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            try {
                String verbs = getVerbs(requestMappingInfo);
                String paths = getPath(requestMappingInfo);
                String prods = getProds(requestMappingInfo);

                List<String> endp = endpoints.computeIfAbsent(prods, k -> new ArrayList<>());
                endp.add("[ " +  String.format("%-6s", verbs)  + " ] \t " + paths);

            } catch (Exception ex) {
                System.out.println("[ERRO] " + requestMappingInfo);
            }
        });

        System.out.println("\n[ ENDPOINTS DISPONÍVEIS ]\n");
        endpoints.forEach((k, v) -> {
            System.out.println("[" + k + "]");
            v.forEach(e -> System.out.println("  " + e));
            System.out.println();
        });
    }

    private String getPath(RequestMappingInfo requestMappingInfo) {
        List<String> list = new ArrayList<>();
        Set<PathPattern> pathPattern = null;
        PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
        if (pathPatternsCondition != null) {
            pathPattern = pathPatternsCondition.getPatterns();
        }
        if (pathPattern != null) {
            for (PathPattern obj : pathPattern) {
                list.add(obj.getPatternString());
            }
        }
        return String.join("   ", list);
    }

    private String getVerbs(RequestMappingInfo requestMappingInfo) {
        List<String> list = new ArrayList<>();
        Set<RequestMethod> requestMethod = requestMappingInfo.getMethodsCondition().getMethods();
        for (RequestMethod obj : requestMethod) {
            list.add(obj.toString());
        }
        return String.join(" - ", list);
    }

    private String getProds(RequestMappingInfo requestMappingInfo) {
        List<String> list = new ArrayList<>();
        Set<MediaType> mediaType = requestMappingInfo.getProducesCondition().getProducibleMediaTypes();
        for (MediaType obj : mediaType) {
            list.add(obj.toString());
        }
        if (list.isEmpty()) {
            return keyNotProd;
        }
        return String.join(" - ", list);
    }
}
