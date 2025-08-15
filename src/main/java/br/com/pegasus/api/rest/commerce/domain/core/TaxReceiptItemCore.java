package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.TaxReceiptItemJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaxReceiptItemCore implements TaxReceiptItemPort {

    private final TaxReceiptItemJpaAdapter taxReceiptItemJpa;
}
