package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;

public class TaxReceiptCore implements TaxReceiptPort {

  private final TaxReceiptDBAdapter taxReceiptJpa;
  private final CooperatorDBAdapter coopJpa;
  private final ValidMethodAdapter validMethod;
  private final ExceptionMethodAdapter exMethod;

  public TaxReceiptCore(ToolKitAdapter tools) {
    this.taxReceiptJpa = tools.getTaxReceiptRepository();
    this.coopJpa = tools.getCooperatorRepository();
    this.validMethod = tools.getValidMethod();
    this.exMethod = tools.getExceptionMethod();
  }

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptModel> findPage(PageModel inModel) {
    return taxReceiptJpa.findPage(inModel);
  }

  @LogAnnot
  @Override
  public TaxReceiptModel findById(TaxReceiptModel inModel) {
    return taxReceiptJpa.findById(inModel)//
        .orElseThrow(exMethod::newNotFound);
  }

  @LogAnnot
  @Override
  public TaxReceiptModel create(TaxReceiptModel inModel) {
    checkExistenceCooperator(inModel);
    return taxReceiptJpa.create(inModel);
  }

  private void checkExistenceCooperator(TaxReceiptModel inModel) {
    coopJpa.findById(CooperatorModel.builder().id(inModel.getCooperatorId()).build())//
        .orElseThrow(exMethod::newCooperatorNotFound);
  }

}
