package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.infra.data.DependencePropPomData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class AppStatusConfig {

  private final RequestMappingHandlerMapping handlerMapping;

  public AppStatusConfig(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  @PostConstruct
  public void init() throws Exception {
    printPom();
    printEndpoints();
    System.out.println();
  }

  public void printPom() throws Exception {
    //COLETA DE DADOS
    NodeList docDependencies = DocumentBuilderFactory.newInstance().newDocumentBuilder()//
        .parse(new File("pom.xml")).getElementsByTagName("dependency");

    // Imprime todas as informações coletadas
    System.out.println("\n╔ ◎ DEPENDÊNCIAS DO PROJETO");

    IntStream.range(0, docDependencies.getLength()).mapToObj(i -> {
      Element elem = (Element) docDependencies.item(i);
      Node versionNode = elem.getElementsByTagName("version").item(0);
      String groupId = elem.getElementsByTagName("groupId").item(0).getTextContent();
      String artifactId = elem.getElementsByTagName("artifactId").item(0).getTextContent();
      String version = (versionNode == null) ? null : versionNode.getTextContent();
      return "║ [" + artifactId + "] [" + groupId + "] [" + version + "]";
    }).forEach(System.out::println);
  }

  public void printEndpoints() {
    System.out.println("\n╔ ◎ ENDPOINTS DISPONÍVEIS");
    handlerMapping.getHandlerMethods().keySet().stream().map(key -> "║ " + key.toString().substring(1,key.toString().length()-1)).forEach(System.out::println);
  }

}
