package br.com.pegasus.api.rest.commerce.infra.config.domain.port;

import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.ProductCore;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansPort {

  @Bean
  public ProductPort createProduct(ToolAdapter tools) {
    return new ProductCore(tools);
  }

}