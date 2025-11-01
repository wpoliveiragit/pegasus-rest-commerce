package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptItemDomainAdapterJPA;
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

  private final TaxReceiptItemDomainAdapterJPA taxReceiptItemJpa;
  private final TaxReceiptDomainAdapterJPA taxReceiptJpa;
  private final MethodDomainAdapter method;
  private final ProductDomainAdaterJPA productJpa;
  private final LogDomainAdapter log;

  public TaxReceiptItemCore(ToolKitAdapter tools) {
    this.taxReceiptItemJpa = tools.getTaxReceiptItemRepository();
    this.taxReceiptJpa = tools.getTaxReceiptRepository();
    this.productJpa = tools.getProductRepository();
    this.method = tools.getMethod();
    this.log = tools.getLog(TaxReceiptItemCore.class);
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptItemModel> findPage(PageModel inModel) {
    log.info("Service ⇉ FindPage");
    PageableModel<TaxReceiptItemModel> responseModel = taxReceiptItemJpa.findPage(inModel);
    log.info("Service ⇇ FindPage");
    return responseModel;
  }

  @LogAnnot
  @Override
  public TaxReceiptItemModel findById(TaxReceiptItemModel inModel) {
    log.info("Service ⇉ FindById");
    TaxReceiptItemModel responseModel = this.getById(inModel);
    log.info("Service ⇇ FindById");
    return responseModel;
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel) {
    log.info("Service ⇉ FindPageByTaxReceiptId");
    PageableModel<TaxReceiptItemModel> responseModel = taxReceiptItemJpa.findPageByTaxReceiptId(inPageModel, inModel);
    log.info("Service ⇇ FindPageByTaxReceiptId");
    return responseModel;
  }

  @LogAnnot
  @Override
  public TaxReceiptItemModel create(TaxReceiptItemModel inModel) {
    log.info("Service ⇉ Create");
    method.validQuantity(inModel.getQuantity());

    TaxReceiptModel taxReceiptModel = TaxReceiptModel.builder()//
        .id(inModel.getTaxReceiptId())//
        .build();
    ProductModel productModel = ProductModel.builder()//
        .id(inModel.getProductId())//
        .build();

    taxReceiptJpa.findById(taxReceiptModel).orElseThrow(method::newTaxReceiptNotFound);
    productJpa.findById(productModel).orElseThrow(method::newProductNotFound);
    this.getById(inModel);
    TaxReceiptItemModel responseModel = taxReceiptItemJpa.create(inModel);
    log.info("Service ⇇ Create");
    return responseModel;
  }

  public TaxReceiptItemModel getById(TaxReceiptItemModel inModel) {
    log.info("Service ⇉ GetById");
    TaxReceiptItemModel responseModel = taxReceiptItemJpa.findById(inModel).orElseThrow(method::newNotFound);
    log.info("Service ⇇ GetById");
    return responseModel;
  }

}