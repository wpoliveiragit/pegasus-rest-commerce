package br.com.pegasus.api.rest.commerce.infra.system;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.util.TextFormatUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PomContextSystem {

  public static void printPom() throws Exception {
    final String fileName = "pom.xml";
    final String tagName = "dependency";
    final String tittle = TextFormatUtil.addColorBlue("◎ DEPENDÊNCIAS DO PROJETO");

    //CRIA A INSTANCIA DO ARQUIVO 'pom.xml'
    File pomFile = new File(fileName);

    //Prepara a lista de tags
    NodeList docDeps = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pomFile)//
        .getElementsByTagName(tagName);

    Stream<String> stringStream = IntStream.range(ConstUtil.INT_0, docDeps.getLength()).mapToObj(i -> createLine((Element) docDeps.item(i)));
    Stream.concat(Stream.of(tittle), stringStream).toList().forEach(System.out::println);
  }

  private static String createLine(Element elem) {
    String groupId = elem.getElementsByTagName("groupId").item(ConstUtil.INT_0).getTextContent();
    String artifactId = elem.getElementsByTagName("artifactId").item(ConstUtil.INT_0).getTextContent();
    Node versionNode = elem.getElementsByTagName("version").item(ConstUtil.INT_0);
    String version = (versionNode == null) ? "VERSÃO PARENT" : versionNode.getTextContent();

    //add color
    groupId = TextFormatUtil.addColorGreen(groupId);
    artifactId = TextFormatUtil.addColorCyan(artifactId);
    version = TextFormatUtil.addColorCyan(version);

    return TextFormatUtil.format("\t- {}: {} - [{}]", groupId, artifactId, version);
  }
}