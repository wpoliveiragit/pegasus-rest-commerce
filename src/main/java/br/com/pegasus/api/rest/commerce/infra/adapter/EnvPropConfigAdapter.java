package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EnvPropConfigAdapter implements EnvPropAdapter {

  private final Environment env;

  @Override
  public String getEnvProp(String key, String valueDefault) {
    return env.getProperty(key, valueDefault);
  }

  @Override
  public String getSpringBootVersion() {
    return SpringBootVersion.getVersion();
  }

  @Override
  public int getJavaVersion() {
    return Runtime.version().feature();
  }
}
