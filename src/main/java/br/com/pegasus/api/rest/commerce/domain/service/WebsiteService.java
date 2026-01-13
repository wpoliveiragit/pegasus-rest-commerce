package br.com.pegasus.api.rest.commerce.domain.service;

import br.com.pegasus.api.rest.commerce.domain.adapter.EnvPropAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.WebsiteCore;
import br.com.pegasus.api.rest.commerce.domain.port.WebsitePort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebsiteService implements WebsitePort {

  private final WebsiteCore core;

  public WebsiteService(EnvPropAdapter envProp) {
    this.core = new WebsiteCore(envProp);
  }

  @Cacheable(value = "website-cache", key = "'id:' + #cache")
  @Override
  public Map<String, ?> getProp(int page) {
    return core.getProp(page);
  }

  @Override
  public Map<String, String> geth2ConsoleInfo(int page) {
    return core.geth2ConsoleInfo(page);
  }


}