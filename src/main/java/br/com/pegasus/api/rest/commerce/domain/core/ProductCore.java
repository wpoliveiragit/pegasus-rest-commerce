package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;

public class ProductCore implements ProductPort {

  private final ProductDBAdapter productJpa;
  private final ExceptionMethodAdapter exMethod;
  private final ValidMethodAdapter validMethod;

  public ProductCore(ToolKitAdapter tools) {
    this.productJpa = tools.getProductRepository();
    this.validMethod = tools.getValidMethod();
    this.exMethod = tools.getExceptionMethod();
  }

  @LogAnnot
  @Override
  public PageableModel<ProductModel> findPage(PageModel inModel) {
    return productJpa.findPage(inModel);
  }

  @LogAnnot
  @Override
  public ProductModel findById(ProductModel inModel) {
    return this.getById(inModel);
  }

  @LogAnnot
  @Override
  public ProductModel create(ProductModel inModel) {
    checkNameConflict(inModel);
    validMethod.validPrice(inModel.getPrice());
    validMethod.validQuantity(inModel.getQuantity());
    return productJpa.create(inModel);
  }

  @LogAnnot
  @Override
  public void update(ProductModel inModel) {
    ProductModel upModel = this.getById(inModel);
    boolean update = false;

    String name = inModel.getName();
    if (validMethod.isNotBlank(inModel.getName())) {
      this.checkNameConflict(inModel);
      upModel.setName(name);
      update = true;
    }

    Float price = inModel.getPrice();
    if (price != null) {
      validMethod.validPrice(price);
      upModel.setPrice(price);
      update = true;
    }

    Integer quantity = inModel.getQuantity();
    if (quantity != null) {
      validMethod.validQuantity(quantity);
      upModel.setQuantity(quantity);
      update = true;
    }

    if (update) {
      productJpa.update(upModel);
    }
  }

  @LogAnnot
  @Override
  public void delete(ProductModel inModel) {
    productJpa.delete(getById(inModel));
  }

  private void checkNameConflict(ProductModel inModel) {
    productJpa.findByName(inModel).ifPresent(obj -> exMethod.throwConflictName());
  }

  private ProductModel getById(ProductModel inModel) {
    return productJpa.findById(inModel).orElseThrow(exMethod::newNotFound);
  }

}