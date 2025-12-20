package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PropertiesModel;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@ComponentLayerMarker("Infra.adapter.EnvProp")
@RequiredArgsConstructor
@Component
public class EnvPropConfigAdapter implements EnvPropAdapter {

  private final Environment env;

  @Override
  public PropertiesModel getEnvProp() {
    return PropertiesModel.builder()//
        .javaVersion(Runtime.version().feature())//
        .springVersion(SpringBootVersion.getVersion())//
        .name("Pegasus")
        .email("contato@pegasus.com.br")
        .SAC("(11) 1234-5678")
        .build();
  }

}
