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
  public void printPom() throws Exception {
    //COLETA DE DADOS
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("pom.xml"));
    NodeList docDependencies = doc.getElementsByTagName("dependency");
    List<DependencePropPomData>  list = IntStream.range(0, docDependencies.getLength()).mapToObj(i -> {
      Element elem = (Element) docDependencies.item(i);
      Node versionNode = elem.getElementsByTagName("version").item(0);
      String groupId = elem.getElementsByTagName("groupId").item(0).getTextContent();
      String artifactId = elem.getElementsByTagName("artifactId").item(0).getTextContent();
      String version = (versionNode == null) ? null : versionNode.getTextContent();
      return DependencePropPomData.builder().groupId(groupId).artifactId(artifactId).version(version).build();
    }).toList();

    //Imprime todas as informações coletadas
    System.out.println("-= Dependencias do projeto =-");
    list.forEach(e -> {
      System.out.println();
      System.out.println(e.getGroupId());
      System.out.println(e.getArtifactId());
      System.out.println(e.getVersion());
      System.out.println(e.getVersion());
      System.out.println(e.getDescription());
    });
    System.out.println("-= Dependencias do projeto =-");
  }

  @PostConstruct
  public void printEndpoints() {
    List<String> lines = handlerMapping.getHandlerMethods().keySet().stream().map(key -> "║#☉#" + key.toString()).toList();
    int maxLine = lines.stream().mapToInt(String::length).max().orElse(0) + 1;

    // printTop
    String baseTitle = String.format("%-" + (maxLine + 1) + "s", "\n╔═#ENDPOINTS DISPONÍVEIS#");
    System.out.println(baseTitle.replace(' ', '═').replace('#', ' ') + "╗");
    // printLine
    lines.forEach(e -> System.out.println(String.format("%-" + maxLine + "s", e).replace('#', ' ') + "║"));
    // printEndLine
    System.out.println(String.format("%-" + maxLine + "s", "╚").replace(' ', '═') + "╝");
  }

}
