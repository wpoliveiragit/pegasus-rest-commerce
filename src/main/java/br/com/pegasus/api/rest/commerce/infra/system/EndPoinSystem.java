package br.com.pegasus.api.rest.commerce.infra.system;

import br.com.pegasus.api.rest.commerce.infra.util.TextUtil;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EndPoinSystem {

  private static List<String> list;

  public static void printEndpoints(RequestMappingHandlerMapping handlerMapping) {
    list = new ArrayList<>();

    final String tittle = TextUtil.addColorBlue("◎ ENDPOINTS DISPONÍVEIS");

    Stream<String> stringStream = handlerMapping.getHandlerMethods().keySet().stream().map(EndPoinSystem::createLine);
    Stream.concat(Stream.of(tittle), stringStream).toList().forEach(System.out::println);
  }

  private static String createLine(RequestMappingInfo key) {
    String line = key.toString().substring(1, key.toString().length() - 1).trim();
    list.add(line);
    return TextUtil.addColorGreen("\t- " + line);
  }

}