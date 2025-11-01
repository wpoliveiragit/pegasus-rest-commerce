package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.CooperatorDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;

public class TaxReceiptCore implements TaxReceiptPort {

  private final TaxReceiptDomainAdapterJPA taxReceiptJpa;
  private final CooperatorDomainAdapterJPA coopJpa;
  private final LogDomainAdapter log;
  private final MethodDomainAdapter method;

  public TaxReceiptCore(ToolKitAdapter tools) {
    this.taxReceiptJpa = tools.getTaxReceiptRepository();
    this.coopJpa = tools.getCooperatorRepository();
    this.log = tools.getLog(TaxReceiptCore.class);
    this.method = tools.getMethod();
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptModel> findPage(PageModel inModel) {
    log.info("Service ⇉ FindPage");
    PageableModel<TaxReceiptModel> responseModel = taxReceiptJpa.findPage(inModel);
    log.info("Service ⇇ FindPage");
    return responseModel;
  }

  @LogAnnot
  @Override
  public TaxReceiptModel findById(TaxReceiptModel inModel) {
    log.info("Service ⇉ FindById");
    TaxReceiptModel responseModel = taxReceiptJpa.findById(inModel).orElseThrow(method::newNotFound);
    log.info("Service ⇇ FindById");
    return responseModel;
  }

  @LogAnnot
  @Override
  public TaxReceiptModel create(TaxReceiptModel inModel) {
    log.info("Service ⇉ Create");
    checkExistenceCooperator(inModel);
    TaxReceiptModel responseModel = taxReceiptJpa.create(inModel);
    log.info("Service ⇇ Create");
    return responseModel;
  }

  private void checkExistenceCooperator(TaxReceiptModel inModel) {
    log.info("Service ⇉ CheckExistenceCooperator");
    CooperatorModel cooperatorModel = CooperatorModel.builder().id(inModel.getCooperatorId()).build();
    log.info("Service ⇇ CheckExistenceCooperator");
    coopJpa.findById(cooperatorModel).orElseThrow(method::newCooperatorNotFound);
  }

}