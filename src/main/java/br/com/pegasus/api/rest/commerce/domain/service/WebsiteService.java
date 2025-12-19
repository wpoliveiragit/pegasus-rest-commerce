package br.com.pegasus.api.rest.commerce.domain.service;


import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.WebsiteCore;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.ComponentLayerMarker;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@ComponentLayerMarker("Service.website")
public class WebsiteService implements WebsitePort {

  private final WebsiteCore core;

  public WebsiteService(EnvPropAdapter envProp) {
    this.core = new WebsiteCore(envProp);
  }

  @Override
  @Cacheable(value = "website-cache", key = "'id:' + #cache")
  public Map<String, ?> info(int page) {
    return core.info(page);
  }
}
