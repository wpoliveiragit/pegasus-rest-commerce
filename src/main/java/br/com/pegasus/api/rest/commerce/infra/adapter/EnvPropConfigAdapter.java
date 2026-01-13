package br.com.pegasus.api.rest.commerce.infra.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
//@ComponentLayerMarker("Infra.adapter.EnvProp")
@RequiredArgsConstructor
@Component
public class EnvPropConfigAdapter implements EnvPropAdapter {

  private final Environment env;

  @Override
  public Object getPropertyMap(String key) {
    ResolvableType type = ResolvableType.forClassWithGenerics(//
        List.class, ResolvableType.forClassWithGenerics(Map.class, String.class, String.class));
    return Binder.get(env).bind(key, Bindable.of(type)).orElse(List.of());
  }

  public List<String> getPropertyList(String key) {
    return Binder.get(env).bind(key, Bindable.listOf(String.class)).orElse(List.of());
  }

  @Override
  public Map<String, String> getH2ConsoleInfo(int page) {
    return Map.of();
  }

}