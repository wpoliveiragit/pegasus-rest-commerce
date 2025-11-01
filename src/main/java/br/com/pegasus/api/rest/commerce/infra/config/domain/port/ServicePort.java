package br.com.pegasus.api.rest.commerce.infra.config.domain.port;

import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.CooperatorCore;
import br.com.pegasus.api.rest.commerce.domain.core.ProductCore;
import br.com.pegasus.api.rest.commerce.domain.core.TaxReceiptCore;
import br.com.pegasus.api.rest.commerce.domain.core.TaxReceiptItemCore;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicePort {

  @Bean
  public CooperatorPort createCooperator(ToolKitAdapter tools) {
    return new CooperatorCore(tools);
  }

  @Bean
  public ProductPort createProduct(ToolKitAdapter tools) {
    return new ProductCore(tools);
  }

  @Bean
  public TaxReceiptItemPort createTaxReceiptItem(ToolKitAdapter tools) {
    return new TaxReceiptItemCore(tools);
  }

  @Bean
  public TaxReceiptPort createTaxReceipt(ToolKitAdapter tools) {
    return new TaxReceiptCore(tools);
  }

}