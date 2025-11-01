package br.com.pegasus.api.rest.commerce.infra.config.domain.port;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.CooperatorDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptItemDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.infra.config.domain.LogDomain;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolKitPort implements ToolKitAdapter {

  private final MethodDomainAdapter validMethod;

  private final CooperatorDomainAdapterJPA cooperatorJpa;
  private final ProductDomainAdaterJPA productJpa;
  private final TaxReceiptItemDomainAdapterJPA taxReceiptItemJpa;
  private final TaxReceiptDomainAdapterJPA taxReceiptJpa;

  /**
   * Retorna um {@link LogDomainAdapter} para a classe informada.
   *
   * @param clazz classe alvo do log
   * @return logger da classe
   */
  @Override
  public LogDomainAdapter getLog(Class<?> clazz) {
    return new LogDomain(LogManager.getLogger(clazz.getSimpleName()));
  }

  @Override
  public CooperatorDomainAdapterJPA getCooperatorRepository() {
    return cooperatorJpa;
  }

  @Override
  public ProductDomainAdaterJPA getProductRepository() {
    return productJpa;
  }

  @Override
  public TaxReceiptItemDomainAdapterJPA getTaxReceiptItemRepository() {
    return taxReceiptItemJpa;
  }

  @Override
  public TaxReceiptDomainAdapterJPA getTaxReceiptRepository() {
    return taxReceiptJpa;
  }

  @Override
  public MethodDomainAdapter getMethod() {
    return validMethod;
  }

}