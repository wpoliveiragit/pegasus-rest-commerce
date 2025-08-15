package br.com.pegasus.api.rest.commerce.infra.config.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.ProductJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter  implements ProductJpaAdapter {
}
