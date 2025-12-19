package br.com.pegasus.api.rest.commerce.infra.system;

import br.com.pegasus.api.rest.commerce.infra.util.TextFormatUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BeanSystem {

  public static void checkBeans(List<Class<?>> beans) {
    final String tittle = TextFormatUtil.addColorBlue("◎ CHECK BEANS");

    Stream<String> stringStream = beans.stream().map(bean -> TextFormatUtil.addColorGreen("\t- " + bean.getSimpleName()));
    Stream.concat(Stream.of(tittle), stringStream).toList().forEach(System.out::println);
  }

}