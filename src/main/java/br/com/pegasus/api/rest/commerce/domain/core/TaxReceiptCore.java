package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.TaxReceiptJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaxReceiptCore implements TaxReceiptPort {

    private final TaxReceiptJpaAdapter taxReceiptJpa;
}
