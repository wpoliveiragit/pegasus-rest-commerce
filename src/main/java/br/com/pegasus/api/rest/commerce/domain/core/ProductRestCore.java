package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.KafkaAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;

import java.time.OffsetDateTime;

public class ProductRestCore implements ProductPort {

  private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

  private String methodCheckName = null;
  private String methodInternalFindById = null;

  private final ProductAdaterJPA productJpa;
  private final MethodAdapter method;
  private final LogAdapter log;
  private final KafkaAdapter kafka;

  public ProductRestCore(ToolAdapter tools) {
    this.productJpa = tools.getProductRepository();
    this.method = tools.getMethod();
    this.log = tools.getLog(ProductRestCore.class);
    this.kafka = tools.getKafka();
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
    OffsetDateTime offsetDateTimeNow = method.getOffsetDateTimeNow();
    checkNameConflict(request);
    ProductModel product = request.getProduct();
    product.setCreatedAt(offsetDateTimeNow);
    product.setUpdatedAt(offsetDateTimeNow);
    ProductModel response = productJpa.create(request);
    kafka.sendProductLifecycleEvents(ConstUtil.KAFKA_CREATE_EVENT, response.getId());
    return response;
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
    kafka.sendProductLifecycleEvents(ConstUtil.KAFKA_UPDATE_EVENT, request.getProduct().getId());
  }

  @Override
  public void delete(RequestModel request) {
    request.setProduct(internalFindById(request));
    productJpa.delete(request);
    kafka.sendProductLifecycleEvents(ConstUtil.KAFKA_DELETE_EVENT, request.getProduct().getId());
  }

  private ProductModel internalFindById(RequestModel request) {
    if (this.methodInternalFindById == null) {
      this.methodInternalFindById = STACK_WALKER.walk(//
          stream -> stream.findFirst().map(StackWalker.StackFrame::getMethodName).orElse("unknown"));
    }

    log.startTrack(ProductRestCore.class, methodInternalFindById);
    ProductModel model = productJpa.findById(request).orElseThrow(method::newNotFoundException);
    log.endTrack(ProductRestCore.class, methodInternalFindById);
    return model;
  }

  private void checkNameConflict(RequestModel request) throws AppException {
    if (this.methodCheckName == null) {
      this.methodCheckName = STACK_WALKER.walk(//
          stream -> stream.findFirst().map(StackWalker.StackFrame::getMethodName).orElse("unknown"));
    }
    log.startTrack(ProductRestCore.class, this.methodCheckName);
    productJpa.findByName(request).ifPresent(obj -> method.throwConflictNameException());
    log.endTrack(ProductRestCore.class, this.methodCheckName);
  }

}