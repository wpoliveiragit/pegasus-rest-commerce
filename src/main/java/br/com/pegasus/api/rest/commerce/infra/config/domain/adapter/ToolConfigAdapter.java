package br.com.pegasus.api.rest.commerce.infra.config.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolConfigAdapter implements ToolAdapter {

  private final ExceptionMethodAdapter validMethod;
  private final ProductAdaterJPA productJpa;
  private final TrackLogger trackLog;

  @Override
  public LogAdapter getLog(Class<?> clazz) {
    return new LogConfigAdapter(trackLog, trackLog.getLogger(clazz));
  }

  @Override
  public ProductAdaterJPA getProductRepository() {
    return productJpa;
  }

  @Override
  public ExceptionMethodAdapter getMethod() {
    return validMethod;
  }

}