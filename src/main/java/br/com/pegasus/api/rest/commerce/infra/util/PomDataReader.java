package br.com.pegasus.api.rest.commerce.infra.util;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Log4j2
public class PomDataReader {
  final XPath xpath;
  final Document pomDoc;
  final NodeList dependency;
  final NodeList plugins;

  public PomDataReader() throws Exception {
    xpath = XPathFactory.newInstance().newXPath();
    pomDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("pom.xml"));
    dependency = (NodeList) xpath.evaluate("/project/dependencies/dependency", pomDoc, XPathConstants.NODESET);
    plugins = (NodeList) xpath.evaluate("/project/build/plugins/plugin", pomDoc, XPathConstants.NODESET);
  }

  /**
   * Avalia uma expressão XPath em um documento XML.
   *
   * @param key expressão XPath a ser avaliada (ex: {@code "/project/version"})
   * @return valor encontrado como {@link String} ou {@code "not-found"} em caso de falha.
   */
  public String getProperty(String key) {
    try {
      return this.xpath.evaluate(key, pomDoc);
    } catch (Exception ex) {
      log.warn(ex.getMessage());
    }
    return "not-found";
  }

  public List<Map<String, String>> getDependencies() {
    return IntStream.range(0, dependency.getLength())
        .mapToObj(i -> (Element) dependency.item(i))
        .map(el -> Map.of(
            "groupId", getTagValue(el, "groupId"),
            "artifactId", getTagValue(el, "artifactId"),
            "version", getTagValue(el, "version"),
            "description", "não implementado!"
        ))
        .toList();
  }

  public List<Map<String, String>> getPlugins() {
    return IntStream.range(0, plugins.getLength())
        .mapToObj(i -> (Element) plugins.item(i))
        .map(el -> Map.of(
            "groupId", getTagValue(el, "groupId"),
            "artifactId", getTagValue(el, "artifactId"),
            "version", getTagValue(el, "version"),
            "description", "não implementado!"
        ))
        .toList();
  }

  /**
   * Obtém o valor textual da primeira ocorrência de uma tag dentro de um elemento XML.
   *
   * @param element elemento onde a busca será feita
   * @param tag nome da tag a ser procurada
   * @return conteúdo textual da tag ou {@code null} se não existir.
   */
  private static String getTagValue(Element element, String tag) {
    NodeList list = element.getElementsByTagName(tag);
    return list.getLength() > 0 ? list.item(0).getTextContent() : "not-found";
  }


}
