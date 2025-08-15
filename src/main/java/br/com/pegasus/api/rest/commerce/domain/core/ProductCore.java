package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ProductJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCore implements ProductPort {

    private final ProductJpaAdapter productJpa;
}
