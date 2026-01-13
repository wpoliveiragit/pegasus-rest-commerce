package br.com.pegasus.api.rest.commerce.infra.util;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

public class EnvironmentReaderUtil {

  final Environment env;
  final Map<String, Object> databasePropYmlMap;

  public EnvironmentReaderUtil(Environment env) {
    this.env = env;
    this.databasePropYmlMap = YamlLoader.load("properties/database-prop.yml");
  }

  public String getProperty(String key) {
    return this.env.getProperty(key, "not-fount");
  }

  public List<String> getPropertyList(String key) {
    return Binder.get(env).bind(key, Bindable.listOf(String.class)).orElse(List.of());
  }

  public List<?> getPropertyDatabaseMetadata() {
    if (databasePropYmlMap.get("api.database.metadata") instanceof List<?> list) {
      return list.stream().filter(Map.class::isInstance).map(Map.class::cast).map(map -> {
        String propName = map.get("key").toString();
        return Map.of(//
            "key", propName,//
            "value", this.getProperty(propName),//
            "content", map.get("content").toString());
      }).toList();
    }
    return List.of();
  }

}
