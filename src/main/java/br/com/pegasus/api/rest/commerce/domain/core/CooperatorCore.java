package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.CooperatorDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;

public class CooperatorCore implements CooperatorPort {

  private final CooperatorDomainAdapterJPA coopJpa;
  private final MethodDomainAdapter method;
  private final LogDomainAdapter log;

  public CooperatorCore(ToolKitAdapter tools) {
    this.coopJpa = tools.getCooperatorRepository();
    this.method = tools.getMethod();
    this.log = tools.getLog(CooperatorCore.class);
  }

  @Override
  public PageableModel<CooperatorModel> findPage(PageModel inModel) {
    log.info("service ⇉ FindPage");
    PageableModel<CooperatorModel> response = coopJpa.findPage(inModel);
    log.info("service ⇇ FindPage");
    return response;
  }

  @Override
  public CooperatorModel findById(CooperatorModel inModel) {
    log.info("service ⇉ FindById");
    var response = this.getById(inModel);
    log.info("service ⇇ FindById");
    return response;
  }

  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    log.info("service ⇉ Create");
    var response = coopJpa.create(inModel);
    this.checkDocumentNumberConflict(inModel);
    log.info("service ⇇ Create");
    return response;
  }

  @Override
  public void update(CooperatorModel inModel) {
    log.info("service ⇉ Update");
    CooperatorModel upModel = this.getById(inModel);
    boolean update = false;

    String name = inModel.getName();
    if (method.isNotBlank(inModel.getName())) {
      upModel.setName(name);
      update = true;
    }

    String docNum = inModel.getDocumentNumber();
    if (method.isNotBlank(docNum)) {
      this.checkDocumentNumberConflict(inModel);
      upModel.setDocumentNumber(docNum);
      update = true;
    }
    if (update) {
      coopJpa.update(upModel);
    }
    log.info("service ⇇ Update");
  }

  @Override
  public void delete(CooperatorModel inModel) {
    log.info("service ⇉ Delete");
    coopJpa.delete(getById(inModel));
    log.info("service ⇇ Delete");
  }

  private void checkDocumentNumberConflict(CooperatorModel inModel) {
    log.info("service ⇉ CheckDocumentNumberConflict");
    method.validDocumentNumber(inModel.getDocumentNumber());
    coopJpa.findByDocumentNumber(inModel).ifPresent(e -> method.throwConflictDocumentNumber());
    log.info("service ⇇ CheckDocumentNumberConflict");
  }

  private CooperatorModel getById(CooperatorModel inModel) {
    log.info("service ⇉ GetById");
    CooperatorModel responseModel = coopJpa.findById(inModel).orElseThrow(method::newNotFound);
    log.info("service ⇇ GetById");
    return responseModel;
  }

}
