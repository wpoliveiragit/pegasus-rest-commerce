package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.DataModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;

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
  public PageableModel<ProductModel> findAll(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ findAll", traceId);
    PageableModel<ProductModel> response = productJpa.findAll(request);
    log.info("[{}] Service ⇇ findAll", traceId);
    return response;
  }

  @Override
  public ProductModel findById(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ findById", traceId);
    ProductModel response = this.internalFindById(request);
    log.info("[{}] Service ⇇ findById", traceId);
    return response;
  }

  @Override
  public ProductModel create(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ create", traceId);
    checkNameConflict(request);
    ProductModel response = productJpa.create(request);
    log.info("[{}] Service ⇇ create", traceId);
    return response;
  }

  @Override
  public void update(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ Update", traceId);
    ProductModel originalModel = this.internalFindById(request);
    ProductModel updateModel = request.getProduct();
    if(!originalModel.getName().equalsIgnoreCase(updateModel.getName())){
      this.checkNameConflict(request);
    }
    updateModel.setCreatedAt(originalModel.getCreatedAt());
    productJpa.update(request);
    log.info("[{}] Service ⇇ Update", traceId);
  }

  @Override
  public void delete(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ Delete", traceId);
    request.setProduct(internalFindById(request));
    productJpa.delete(request);
    log.info("[{}] Service ⇇ Delete", traceId);
  }

  private ProductModel internalFindById(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ internalFindById", traceId);
    ProductModel response = productJpa.findById(request).orElseThrow(exMethod::newNotFound);
    log.info("[{}] Service ⇇ internalFindById", traceId);
    return response;
  }

  private void checkNameConflict(DataModel request) {
    String traceId = request.getXTraceId();
    log.info("[{}] Service ⇉ checkNameConflict", traceId);
    productJpa.findByName(request).ifPresent(obj -> exMethod.throwConflictName());
    log.info("[{}] Service ⇇ checkNameConflict", traceId);
  }

}