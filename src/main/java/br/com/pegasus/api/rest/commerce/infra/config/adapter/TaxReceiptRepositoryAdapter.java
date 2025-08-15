package br.com.pegasus.api.rest.commerce.infra.config.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.TaxReceiptJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaxReceiptRepositoryAdapter implements TaxReceiptJpaAdapter {
}
