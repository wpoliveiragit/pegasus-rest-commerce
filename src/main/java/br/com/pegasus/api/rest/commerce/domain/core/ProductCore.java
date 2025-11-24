package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;

import java.time.OffsetDateTime;

public class ProductCore implements ProductPort {

  private final ProductAdaterJPA productJpa;
  private final ExceptionMethodAdapter exMethod;
  private final LogAdapter log;

  public ProductCore(ToolAdapter tools) {
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
    if (!originalModel.getName().equalsIgnoreCase(updateModel.getName())) {// update do nome
      this.checkNameConflict(request);
    }
    updateModel.setCreatedAt(originalModel.getCreatedAt());
    updateModel.setUpdatedAt(OffsetDateTime.now());
    productJpa.update(request);
  }

  @Override
  public void delete(RequestModel request) {
    request.setProduct(internalFindById(request));
    productJpa.delete(request);
  }

  private ProductModel internalFindById(RequestModel request) {
    log.track("● Service.Product.(internalFindById)");
    ProductModel model = productJpa.findById(request).orElseThrow(exMethod::newNotFound);
    log.track("◎ Service.Product.(internalFindById)");
    return model;
  }

  private void checkNameConflict(RequestModel request) throws AppException {
    log.track("● Service.Product(checkNameConflict)");
    productJpa.findByName(request).ifPresent(obj -> exMethod.throwConflictName());
    log.track("◎ Service.Product(checkNameConflict)");
  }

}