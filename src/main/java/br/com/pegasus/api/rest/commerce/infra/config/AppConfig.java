package br.com.pegasus.api.rest.commerce.infra.config;

import br.com.pegasus.api.rest.commerce.domain.adapter.CooperatorJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ProductJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.TaxReceiptItemJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.TaxReceiptJpaAdapter;
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
public class AppConfig {

    @Bean
    public CooperatorPort createCooperator(CooperatorJpaAdapter jpa) {
        return new CooperatorCore(jpa);
    }

    @Bean
    public ProductPort createProduct(ProductJpaAdapter jpa) {
        return new ProductCore(jpa);
    }

    @Bean
    public TaxReceiptItemPort createTaxReceiptItem(TaxReceiptItemJpaAdapter jpa) {
        return new TaxReceiptItemCore(jpa);
    }

    @Bean
    public TaxReceiptPort createTaxReceipt(TaxReceiptJpaAdapter jpa) {
        return new TaxReceiptCore(jpa);
    }

}
