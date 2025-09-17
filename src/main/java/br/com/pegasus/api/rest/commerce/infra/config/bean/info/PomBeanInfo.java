package br.com.pegasus.api.rest.commerce.infra.config.bean.info;

import br.com.pegasus.api.rest.commerce.infra.prop.pom.DependencePropPom;
import br.com.pegasus.api.rest.commerce.infra.prop.pom.PomPropPom;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Getter
public class PomBeanInfo {

  private final PomPropPom pom;

  public PomBeanInfo() throws Exception {
    pom = collectDataPom();
  }

  private PomPropPom collectDataPom() throws Exception {
    return PomPropPom.builder().dependencies(collectDataDependenciesPom()).build();
  }

  private List<DependencePropPom> collectDataDependenciesPom() throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("pom.xml"));
    NodeList docDependencies = doc.getElementsByTagName("dependency");
    return IntStream.range(0, docDependencies.getLength()).mapToObj(i -> {
      Element elem = (Element) docDependencies.item(i);
      Node versionNode = elem.getElementsByTagName("version").item(0);
      return DependencePropPom.builder()
          .groupId(elem.getElementsByTagName("groupId").item(0).getTextContent())
          .artifactId(elem.getElementsByTagName("artifactId").item(0).getTextContent())
          .version((versionNode == null) ? null : versionNode.getTextContent())
          .build();
    }).toList();
  }

}
