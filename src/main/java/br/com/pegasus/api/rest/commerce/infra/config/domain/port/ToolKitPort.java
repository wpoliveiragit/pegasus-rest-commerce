package br.com.pegasus.api.rest.commerce.infra.config.domain.port;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.infra.config.domain.LogDomain;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolKitPort implements ToolKitAdapter {

  private final ExceptionMethodDomainAdapter validMethod;

  private final ProductDomainAdaterJPA productJpa;

  @Override
  public LogDomainAdapter getLog(Class<?> clazz) {
    return new LogDomain(LogManager.getLogger(clazz.getSimpleName()));
  }


  @Override
  public ProductDomainAdaterJPA getProductRepository() {
    return productJpa;
  }

  @Override
  public ExceptionMethodDomainAdapter getMethod() {
    return validMethod;
  }

}