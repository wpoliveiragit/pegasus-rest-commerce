package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;

import java.time.LocalDateTime;
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
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ findAll", traceId);
    PageableModel<ProductModel> response = productJpa.findAll(request);
//    log.info("[{}] Service ⇇ findAll", traceId);
    return response;
  }

  @Override
  public ProductModel findById(RequestModel request) {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ findById", traceId);
    ProductModel response = this.internalFindById(request);
//    log.info("[{}] Service ⇇ findById", traceId);
    return response;
  }

  @Override
  public ProductModel create(RequestModel request) {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ create", traceId);
    checkNameConflict(request);
    ProductModel product = request.getProduct();
    product.setCreatedAt(OffsetDateTime.now());
    product.setUpdatedAt(OffsetDateTime.now());
    ProductModel response = productJpa.create(request);
//    log.info("[{}] Service ⇇ create", traceId);
    return response;
  }

  @Override
  public void update(RequestModel request) {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ Update", traceId);
    ProductModel originalModel = this.internalFindById(request);
    ProductModel updateModel = request.getProduct();
    if(!originalModel.getName().equalsIgnoreCase(updateModel.getName())){
      this.checkNameConflict(request);
    }
    updateModel.setCreatedAt(originalModel.getCreatedAt());
    productJpa.update(request);
//    log.info("[{}] Service ⇇ Update", traceId);
  }

  @Override
  public void delete(RequestModel request) {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ Delete", traceId);
    request.setProduct(internalFindById(request));
    productJpa.delete(request);
//    log.info("[{}] Service ⇇ Delete", traceId);
  }

  private ProductModel internalFindById(RequestModel request) {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ internalFindById", traceId);
    ProductModel response = productJpa.findById(request).orElseThrow(exMethod::newNotFound);
//    log.info("[{}] Service ⇇ internalFindById", traceId);
    return response;
  }

  private void checkNameConflict(RequestModel request)  throws AppException {
    String traceId = request.getXTraceId();
//    log.info("[{}] Service ⇉ checkNameConflict", traceId);
    if(productJpa.findByName(request).isPresent()){
      exMethod.throwConflictName();
    }
//    productJpa.findByName(request).ifPresent(obj -> exMethod.throwConflictName());
//    log.info("[{}] Service ⇇ checkNameConflict", traceId);
  }

}