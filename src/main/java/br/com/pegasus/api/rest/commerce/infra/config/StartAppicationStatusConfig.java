package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.infra.data.DependencePomData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.stream.IntStream;

@Component
public class StartAppicationStatusConfig {

  private final RequestMappingHandlerMapping handlerMapping;

  public StartAppicationStatusConfig(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  @PostConstruct
  public void init() throws Exception {
    System.out.println();
    System.out.println();
    printPom();
    printEndpoints();
    System.out.println();
  }

  public void printPom() throws Exception {

    //COLETA DE DADOS
    NodeList docDependencies = DocumentBuilderFactory.newInstance().newDocumentBuilder()//
        .parse(new File("pom.xml")).getElementsByTagName("dependency");

    // Imprime  todas as informações coletadas
    System.out.println(" ◎ DEPENDÊNCIAS DO PROJETO");

    IntStream.range(0, docDependencies.getLength()).forEach(i -> {
      DependencePomData dep = new DependencePomData((Element) docDependencies.item(i));
      System.out.println(" -" + " [" + dep.getArtifactId() + "]" + " [" + dep.getGroupId() + "]" + " [" + dep.getVersion() + "]");
    });

  }

  public void printEndpoints() {
    System.out.println("\n ◎ ENDPOINTS DISPONÍVEIS");
    handlerMapping.getHandlerMethods().keySet().forEach(key -> {
      System.out.println(" - " + key.toString().substring(1, key.toString().length() - 1).trim());
    });
  }

}
