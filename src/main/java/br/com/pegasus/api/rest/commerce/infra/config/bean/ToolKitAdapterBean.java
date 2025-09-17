package br.com.pegasus.api.rest.commerce.infra.config.bean;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptItemDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ToolKitAdapterBean implements ToolKitAdapter {

  // LOG: gerado dinamicamente no método

  // DB
  private final CooperatorDBAdapter cooperatorJpa;
  private final ProductDBAdapter productJpa;
  private final TaxReceiptItemDBAdapter taxReceiptItemJpa;
  private final TaxReceiptDBAdapter taxReceiptJpa;
  // METHODS
  private final ExceptionMethodAdapter exceptionMethod;
  private final ValidMethodAdapter validMethod;

  @Override
  public LogAdapter getLog(Class<?> c) {
    return new LogAdapterBean(Logger.getLogger(c.getName()));
  }

  @Override
  public CooperatorDBAdapter getCooperatorRepository() {
    return cooperatorJpa;
  }

  @Override
  public ProductDBAdapter getProductRepository() {
    return productJpa;
  }

  @Override
  public TaxReceiptItemDBAdapter getTaxReceiptItemRepository() {
    return taxReceiptItemJpa;
  }

  @Override
  public TaxReceiptDBAdapter getTaxReceiptRepository() {
    return taxReceiptJpa;
  }

  @Override
  public ExceptionMethodAdapter getExceptionMethod() {
    return exceptionMethod;
  }

  @Override
  public ValidMethodAdapter getValidMethod() {
    return validMethod;
  }

}
