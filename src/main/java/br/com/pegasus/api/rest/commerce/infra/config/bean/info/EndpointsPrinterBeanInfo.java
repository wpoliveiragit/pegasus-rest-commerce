package br.com.pegasus.api.rest.commerce.infra.config.bean.info;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.StreamUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    handlerMapping.getHandlerMethods().forEach((reqMap, handlerMethod) -> {
      try {
        String keyList = StreamUtil.of(reqMap.getProducesCondition().getProducibleMediaTypes())//
            .map(Object::toString)//
            .joing(ConstUtil.T_SEP);

        if (keyList.isEmpty()) {
          return;
        }

        String verbs = StreamUtil.of(reqMap.getMethodsCondition().getMethods())//
            .map(Enum::toString)//
            .joing(ConstUtil.T_SEP);

        endpoints.computeIfAbsent(keyList, k -> new ArrayList<>())//
            .add(createEndpointLine(verbs, reqMap));
      } catch (Exception ex) {
        System.out.println("[ERRO] " + reqMap);
      }
    });
    printTable(endpoints);
  }

  private String createEndpointLine(String verbs, RequestMappingInfo reqMap) {
    PathPatternsRequestCondition ppc = reqMap.getPathPatternsCondition();
    String endpoint = (ppc == null) ? //
        "" : StreamUtil.of(ppc.getPatterns())//
        .map(PathPattern::getPatternString)//
        .joing(ConstUtil.T_SEP);

    return MethodUtil.formatL6(verbs) + "\t" + endpoint;
  }

  private void printTable(Map<String, List<String>> endpoints) {
    System.out.println("\n<ENDPOINTS DISPONÍVEIS>");
    endpoints.forEach((key, value) -> System.out.println(" " + key + "\n\t" + String.join("\n\t", value)));
  }

}
