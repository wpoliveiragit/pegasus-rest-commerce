package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.RequiredArgsConstructor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@RequiredArgsConstructor
public class DependencePomData {

  private final Element elem;

  public String getGroupId() {
    return elem.getElementsByTagName("groupId").item(0).getTextContent();
  }

  public String getArtifactId() {
    return elem.getElementsByTagName("artifactId").item(0).getTextContent();
  }

  public String getVersion() {
    Node versionNode = elem.getElementsByTagName("version").item(0);
    return (versionNode == null) ? "VERSÃO PARENT" : versionNode.getTextContent();
  }

}

