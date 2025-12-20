package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PropertiesModel;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import br.com.pegasus.api.rest.commerce.infra.util.TextFormatUtil;

import java.util.Map;

public class WebsiteCore implements WebsitePort {

  private final PropertiesModel prop;

  public WebsiteCore(EnvPropAdapter envProp) {
    prop = envProp.getEnvProp();
  }

  @Override
  public Map<String, ?> website(int page) {
    String block = TextFormatUtil.format("""
            <ul>
                <li>Java: {}</li>
                <li>Spring Boot: {}</li>
            </ul>
            """,//
        prop.getJavaVersion(),//
        prop.getSpringVersion()//
    );
    return concatRootProperties("blocoSobre", block);
  }

  @Override
  public Map<String, ?> license(int page) {
    return getSystemProperties();
  }

  @Override
  public Map<String, ?> terms(int page) {
    return getSystemProperties();
  }

  private String environmentBlock() {
    String block = """
        <h3>Informações da aplicação</h3>
        <ul>
            <li>Java: {}</li>
            <li>Spring Boot: {}</li>
        </ul>
        """;

    return TextFormatUtil.format(block,//
        prop.getJavaVersion(),//
        prop.getSpringVersion()//
    );
  }

  private Map<String, ?> concatRootProperties(String key, String value) {
    return Map.of(//
        "system", getSystemProperties(),//
        key, value//
    );
  }

  private Map<String, ?> getSystemProperties() {
    return Map.of(//
        "javaVersion", prop.getJavaVersion(),//
        "springBootVersion", prop.getSpringVersion(),//
        "email", prop.getEmail(),//
        "SAC", prop.getSAC(),//
        "name", prop.getName()
    );
  }

}