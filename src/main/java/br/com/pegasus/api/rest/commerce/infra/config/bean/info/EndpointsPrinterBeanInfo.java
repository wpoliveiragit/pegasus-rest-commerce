package br.com.pegasus.api.rest.commerce.infra.config.bean.info;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j2
@Component
public class EndpointsPrinterBeanInfo implements CommandLineRunner {

  private final RequestMappingHandlerMapping handlerMapping;

  public EndpointsPrinterBeanInfo(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  @Override
  public void run(String... args) {
    Map<String, List<String>> endpoints = new HashMap<>(1);
    handlerMapping.getHandlerMethods().forEach((reqMapping, handlerMethod) -> {
      try {
        var ppc = reqMapping.getPathPatternsCondition();
        Set<PathPattern> pp = (ppc == null) ? null : ppc.getPatterns();

        List<String> list = new ArrayList<>();
        reqMapping.getProducesCondition().getProducibleMediaTypes().forEach(obj -> list.add(obj.toString()));
        String prods = (list.isEmpty()) ? null : String.join(" - ", list);

        if (prods == null) {
          return;
        }
        String verbs = String.join(" - ", reqMapping.getMethodsCondition().getMethods().stream().map(Enum::toString).toList());
        String paths = String.join("   ", (pp == null)//
            ? new ArrayList<String>() : pp.stream().map(PathPattern::getPatternString).toList());
        endpoints.computeIfAbsent(prods, k -> new ArrayList<>()).add(String.format("%-6s", verbs) + "\t" + paths);
      } catch (Exception ex) {
        System.out.println("[ERRO] " + reqMapping);
      }
    });
    System.out.println("\n<ENDPOINTS DISPONÍVEIS>");
    endpoints.forEach((k, v) -> System.out.println(" " + k + "\n\t" + String.join("\n\t", v)));
  }

}
