package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;

public class CooperatorCore implements CooperatorPort {

  private final CooperatorDBAdapter coopJpa;
  private final ExceptionMethodAdapter exMethod;
  private final ValidMethodAdapter validMethod;

  public CooperatorCore(ToolKitAdapter tools) {
    this.coopJpa = tools.getCooperatorRepository();
    this.validMethod = tools.getValidMethod();
    this.exMethod = tools.getExceptionMethod();
  }

  @LogAnnot
  @Override
  public PageableModel<CooperatorModel> findPage(PageModel inModel) {
    return coopJpa.findPage(inModel);
  }

  @LogAnnot
  @Override
  public CooperatorModel findById(CooperatorModel inModel) {
    return this.getById(inModel);
  }

  @LogAnnot
  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    this.checkDocumentNumberConflict(inModel);
    return coopJpa.create(inModel);
  }

  @LogAnnot
  @Override
  public void update(CooperatorModel inModel) {
    CooperatorModel upModel = this.getById(inModel);
    boolean update = false;

    String name = inModel.getName();
    if (validMethod.isNotBlank(inModel.getName())) {
      upModel.setName(name);
      update = true;
    }

    String docNum = inModel.getDocumentNumber();
    if (validMethod.isNotBlank(docNum)) {
      this.checkDocumentNumberConflict(inModel);
      upModel.setDocumentNumber(docNum);
      update = true;
    }
    if (update) {
      coopJpa.update(upModel);
    }
  }

  @LogAnnot
  @Override
  public void delete(CooperatorModel inModel) {
    coopJpa.delete(getById(inModel));
  }

  private void checkDocumentNumberConflict(CooperatorModel inModel) {
    validMethod.validDocumentNumber(inModel.getDocumentNumber());
    coopJpa.findByDocumentNumber(inModel).ifPresent(e -> exMethod.throwConflictDocumentNumber());
  }

  private CooperatorModel getById(CooperatorModel inModel) {
    return coopJpa.findById(inModel).orElseThrow(exMethod::newNotFound);
  }

}
