package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.StartApplication;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class StartupInfoConfig {

  private final RequestMappingHandlerMapping handlerMapping;
  private final ApplicationContext ctx;

  public StartupInfoConfig(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping, ApplicationContext ctx) {
    this.handlerMapping = handlerMapping;
    this.ctx = ctx;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() throws Exception {
    printPom().forEach(System.out::println);
    printEndpoints().forEach(System.out::println);
    checkBeans().forEach(System.out::println);

    double seconds = (System.currentTimeMillis() - StartApplication.START_TIME) / 1000.0;
    System.out.printf(MethodUtil.addColorGreenText("Started %s in %.3f seconds (process running for %.3f)%n"),//
        StartApplication.class.getSimpleName(), seconds, seconds);
  }

  public List<String> printPom() throws Exception {
    NodeList docDeps = DocumentBuilderFactory.newInstance().newDocumentBuilder()//
        .parse(new File("pom.xml")).getElementsByTagName("dependency");

    return Stream.concat(Stream.of("◎ DEPENDÊNCIAS DO PROJETO"), IntStream.range(0, docDeps.getLength())//
        .mapToObj(i -> createDependencyLine((Element) docDeps.item(i)))//
    ).toList();
  }

  public List<String> printEndpoints() {
    return Stream.concat(Stream.of("◎ ENDPOINTS DISPONÍVEIS"), handlerMapping.getHandlerMethods().keySet()//
        .stream().map(key -> "\t- " + key.toString().substring(1, key.toString().length() - 1).trim())//
    ).toList();
  }

  public List<String> checkBeans() {
    List<Class<?>> beans = List.of();

    return Stream.concat(Stream.of("◎ CHECK BEANS"),//
        beans.stream().map(bean -> checkBean(bean) + bean.getSimpleName())//
    ).toList();
  }

  private String checkBean(Class<?> beanClass) {
    return (ctx.getBeanNamesForType(beanClass).length > 0) ? "\t[X] " : "\t[ ] ";
  }

  private String createDependencyLine(Element elem) {
    String groupId = elem.getElementsByTagName("groupId").item(0).getTextContent();
    String artifactId = elem.getElementsByTagName("artifactId").item(0).getTextContent();
    Node versionNode = elem.getElementsByTagName("version").item(0);
    String version = (versionNode == null) ? "VERSÃO PARENT" : versionNode.getTextContent();

    return "\t- " + groupId + ", " + artifactId + ", " + "[" + version + "]";
  }

}
