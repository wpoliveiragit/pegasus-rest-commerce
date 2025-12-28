package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.HtmlAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.prop.MetadataModelProp;
import br.com.pegasus.api.rest.commerce.domain.model.prop.MetadataPropModel;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsiteCore implements WebsitePort {

  private Map<String, Object> websitePropMap;
  private final MetadataModelProp prop;

  public WebsiteCore(EnvPropAdapter envProp, HtmlAdapter html) {
    this.prop = envProp.getEnvProp();
    websitePropMap = loadPropMap(envProp.getEnvProp());
  }

  @Override
  public Map<String, ?> website(int page) {
    return websitePropMap;
  }

  @Override
  public Map<String, ?> license(int page) {
    return websitePropMap;
  }

  @Override
  public Map<String, ?> terms(int page) {
    return websitePropMap;
  }

  private Map<String, Object> loadPropMap(MetadataModelProp prop) {
    Map<String, Object> websitePropMap = new HashMap<>(12);
    websitePropMap.put("system", loadSystemMap(prop));
    websitePropMap.put("metadata", loadDatabaseMap(prop));
    return websitePropMap;
  }

  private Map<String, String> loadSystemMap(MetadataModelProp prop) {
    return Map.of(//
        "bla", "xxxxxxxxxx",//
        "javaVersion", prop.getJavaVersion(),//
        "springBootVersion", prop.getSpringVersion(),//
        "email", prop.getEmail(),//
        "SAC", prop.getSac(),//
        "name", prop.getName());
  }

  private List<?> loadDatabaseMap(MetadataModelProp prop) {
    return prop.getDataBase().getMetadata();
  }

}