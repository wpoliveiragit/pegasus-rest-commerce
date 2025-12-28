package br.com.pegasus.api.rest.commerce.infra.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlLoader {

  public static Map<String, Object> load(String path) {
    Yaml yaml = new Yaml();
    try (InputStream is = YamlLoader.class.getClassLoader().getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Arquivo não encontrado: " + path);
      }
      return yaml.load(is);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

