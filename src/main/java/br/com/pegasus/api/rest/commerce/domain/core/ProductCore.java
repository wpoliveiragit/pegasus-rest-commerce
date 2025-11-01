package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;

public class ProductCore implements ProductPort {

  private final ProductDomainAdaterJPA productJpa;
  private final MethodDomainAdapter method;
  private final LogDomainAdapter log;

  public ProductCore(ToolKitAdapter tools) {
    this.productJpa = tools.getProductRepository();
    this.method = tools.getMethod();
    this.log = tools.getLog(ProductCore.class);
  }

  @Override
  public PageableModel<ProductModel> findPage(PageModel inModel) {
    log.info("Service ⇉ FindPage");
    PageableModel<ProductModel> response = productJpa.findPage(inModel);
    log.info("Service ⇇ FindPage");
    return response;
  }

  @Override
  public ProductModel findById(ProductModel inModel) {
    log.info("Service ⇉ FindById");
    ProductModel response = this.getById(inModel);
    log.info("Service ⇇ FindById");
    return response;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info("Service ⇉ create");
    checkNameConflict(inModel);
    method.validPriceUpdate(inModel.getPrice());
    method.validQualityUpdate(inModel.getQuantity());
    ProductModel response = productJpa.create(inModel);
    log.info("Service ⇇ create");
    return response;
  }

  @Override
  public void update(ProductModel inModel) {
    log.info("Service ⇉ Update");

    ProductModel upModel = this.getById(inModel);

    final String name = method.validNameUpdate(inModel.getName());
    this.checkNameConflict(inModel);
    final Float price = method.validPriceUpdate(inModel.getPrice());
    final Integer quantity = method.validQualityUpdate(inModel.getQuantity());

    upModel.setName(name);
    upModel.setPrice(price);
    upModel.setQuantity(quantity);

    productJpa.update(upModel);

    log.info("Service ⇇ Update");
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info("Service ⇉ Delete");
    productJpa.delete(getById(inModel));
    log.info("Service ⇇ Delete");
  }

  private void checkNameConflict(ProductModel inModel) {
    log.info("Service ⇉ CheckNameConflict");
    productJpa.findByName(inModel).ifPresent(obj -> method.throwConflictName());
    log.info("Service ⇇ CheckNameConflict");
  }

  private ProductModel getById(ProductModel inModel) {
    log.info("Service ⇉ GetById");
    ProductModel response = productJpa.findById(inModel).orElseThrow(method::newNotFound);
    log.info("Service ⇇ GetById");
    return response;
  }

}