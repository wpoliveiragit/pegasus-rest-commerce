package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;

import java.time.OffsetDateTime;

@LogProxyAnnotation("Service")
public class ProductCore implements ProductPort {

  private final ProductDomainAdaterJPA productJpa;
  private final ExceptionMethodDomainAdapter exMethod;
  private final LogDomainAdapter log;

  public ProductCore(ToolKitAdapter tools) {
    this.productJpa = tools.getProductRepository();
    this.exMethod = tools.getMethod();
    this.log = tools.getLog(ProductCore.class);
  }

  @Override
  public PageableModel<ProductModel> findAll(RequestModel request) {
    return productJpa.findAll(request);
  }

  @Override
  public ProductModel findById(RequestModel request) {
    return this.internalFindById(request);
  }

  @Override
  public ProductModel create(RequestModel request) {
    checkNameConflict(request);
    ProductModel product = request.getProduct();
    product.setCreatedAt(OffsetDateTime.now());
    product.setUpdatedAt(OffsetDateTime.now());
    return productJpa.create(request);
  }

  @Override
  public void update(RequestModel request) {
    ProductModel originalModel = this.internalFindById(request);
    ProductModel updateModel = request.getProduct();
    if (!originalModel.getName().equalsIgnoreCase(updateModel.getName())) {
      this.checkNameConflict(request);
    }
    updateModel.setCreatedAt(originalModel.getCreatedAt());
    productJpa.update(request);
  }

  @Override
  public void delete(RequestModel request) {
    request.setProduct(internalFindById(request));
    productJpa.delete(request);
  }

  private ProductModel internalFindById(RequestModel request) {
    return productJpa.findById(request).orElseThrow(exMethod::newNotFound);
  }

  private void checkNameConflict(RequestModel request) throws AppException {
    productJpa.findByName(request).ifPresent(obj -> exMethod.throwConflictName());
  }

}