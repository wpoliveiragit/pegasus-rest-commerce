package br.com.pegasus.api.rest.commerce.domain.service;

import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.core.ProductCore;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@TelemetryComponentMark("Service.Product")
public class ProductService implements ProductPort {

  private final ProductCore core;

  public ProductService(ToolAdapter tools) {
    core = new ProductCore(tools);
  }

  @Override
  @Cacheable(value = "product-cache", key = "'all:' + #request.page.number + ':' + #request.page.size")
  public PageableModel<ProductModel> findAll(RequestModel request) {
    return core.findAll(request);
  }

  @Override
  @Cacheable(value = "product-cache", key = "'id:' + #request.product.id")
  public ProductModel findById(RequestModel request) {
    return core.findById(request);
  }

  @Override
  @CacheEvict(value = "product-cache", allEntries = true)
  public ProductModel create(RequestModel request) {
    return core.create(request);
  }

  @Override
  @CacheEvict(value = "product-cache", allEntries = true)
  public void update(RequestModel request) {
    core.update(request);
  }

  @Override
  @CacheEvict(value = "product-cache", allEntries = true)
  public void delete(RequestModel request) {

  }
}
