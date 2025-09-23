package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptItemDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaxReceiptItemCore implements TaxReceiptItemPort {

  private final TaxReceiptItemDBAdapter taxReceiptItemJpa;
  private final TaxReceiptDBAdapter taxReceiptJpa;
  private final ValidMethodAdapter validMethod;
  private final ExceptionMethodAdapter exMethod;
  private final ProductDBAdapter productJpa;

  public TaxReceiptItemCore(ToolKitAdapter tools) {
    this.taxReceiptItemJpa = tools.getTaxReceiptItemRepository();
    this.taxReceiptJpa = tools.getTaxReceiptRepository();
    this.productJpa = tools.getProductRepository();
    this.validMethod = tools.getValidMethod();
    this.exMethod = tools.getExceptionMethod();
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptItemModel> findPage(PageModel inModel) {
    return taxReceiptItemJpa.findPage(inModel);
  }

  @LogAnnot
  @Override
  public TaxReceiptItemModel findById(TaxReceiptItemModel inModel) {
    return this.getById(inModel);
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel) {
    return taxReceiptItemJpa.findPageByTaxReceiptId(inPageModel, inModel);
  }

  @LogAnnot
  @Override
  public TaxReceiptItemModel create(TaxReceiptItemModel inModel) {
    validMethod.validQuantity(inModel.getQuantity());
    taxReceiptJpa.findById(TaxReceiptModel.builder().id(inModel.getTaxReceiptId()).build())//
        .orElseThrow(exMethod::newTaxReceiptNotFound);
    productJpa.findById(ProductModel.builder().id(inModel.getProductId()).build())//
        .orElseThrow(exMethod::newProductNotFound);
    this.getById(inModel);
    return taxReceiptItemJpa.create(inModel);
  }

  public TaxReceiptItemModel getById(TaxReceiptItemModel inModel) {
    return taxReceiptItemJpa.findById(inModel).orElseThrow(exMethod::newNotFound);
  }

}