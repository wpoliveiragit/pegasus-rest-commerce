package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.prop.MetadataModelProp;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;


@ComponentLayerMarker("Infra.adapter.EnvProp")
@RequiredArgsConstructor
@Component
public class EnvPropConfigAdapter implements EnvPropAdapter {

  private final Environment env;

  @Override
  public MetadataModelProp getEnvProp() {
    return new MetadataModelProp(env);//
  }

  public String getProperty(String key){
    return env.getProperty(key,"");
  }

  public String getPropertyStringList(String key){
    List<String> list = MethodUtil.envGetPropertyList(env, key);

    return env.getProperty(key,"");
  }

}
