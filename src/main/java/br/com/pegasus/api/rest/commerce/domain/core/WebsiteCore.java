package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import br.com.pegasus.api.rest.commerce.infra.adapter.EnvPropConfigAdapter;
import org.springframework.boot.SpringBootVersion;

import java.util.Map;

public class WebsiteCore implements WebsitePort {

  private final EnvPropAdapter envProp;

  public WebsiteCore(EnvPropAdapter envProp){
    this.envProp = envProp;
  }

  @Override
  public Map<String, ?> info(int page) {

    final int javaVersion = envProp.getJavaVersion();
    final String springVersion = envProp.getSpringBootVersion();

    Map<String, Object> systemMap = Map.of(//
        "javaVersion", javaVersion,//
        "springBootVersion", javaVersion//
    );


    return Map.of(//
        "system", systemMap,//
        "environmentHtml", environmentBlock(javaVersion, springVersion)//
    );
  }

  public static String environmentBlock(int javaVersion, String springBootVersion) {
    StringBuilder html = new StringBuilder();
    html.append("<h3>Ambiente (TESTE)</h3>");//
    html.append("<ul>");//
    html.append("   <li>Java: ").append(javaVersion).append("</li>");//
    html.append("   <li>Spring Boot: ").append(springBootVersion).append("</li>");//
    html.append("</ul>");
    return html.toString();
  }

}
