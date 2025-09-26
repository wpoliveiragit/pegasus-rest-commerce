package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ToolKitAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.ValidMethodAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.vo.CheckLogVO;

public class CooperatorCore implements CooperatorPort {

  private final CooperatorDBAdapter coopJpa;
  private final ExceptionMethodAdapter exMethod;
  private final ValidMethodAdapter validMethod;
  private final LogAdapter log;

  public CooperatorCore(ToolKitAdapter tools) {
    this.coopJpa = tools.getCooperatorRepository();
    this.validMethod = tools.getValidMethod();
    this.exMethod = tools.getExceptionMethod();
    this.log = tools.getLog(CooperatorCore.class);
  }

  @Override
  public PageableModel<CooperatorModel> findPage(CheckLogVO getPagelog, PageModel inModel) {
    getPagelog.addMessage("Start Service");
    var response = coopJpa.findPage(getPagelog, inModel);
    getPagelog.addMessage("Finished Service");
    return response;
  }

  @Override
  public CooperatorModel findById(CooperatorModel inModel) {
    log.info("findById ⇉ STARTED");
    var response = this.getById(inModel);
    log.info("findById ⇉ FINISHED");
    return response;
  }

  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    log.info("create ⇉ STARTED");
    var response = coopJpa.create(inModel);
    this.checkDocumentNumberConflict(inModel);
    log.info("create ⇉ FINISHED");
    return response;
  }

  @Override
  public void update(CooperatorModel inModel) {
    log.info("update ⇉ STARTED");
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
    log.info("update ⇉ FINISHED");
  }

  @Override
  public void delete(CooperatorModel inModel) {
    log.info("delete ⇉ STARTED");
    coopJpa.delete(getById(inModel));
    log.info("delete ⇉ FINISHED");
  }

  private void checkDocumentNumberConflict(CooperatorModel inModel) {
    validMethod.validDocumentNumber(inModel.getDocumentNumber());
    coopJpa.findByDocumentNumber(inModel).ifPresent(e -> exMethod.throwConflictDocumentNumber());
  }

  private CooperatorModel getById(CooperatorModel inModel) {
    return coopJpa.findById(inModel).orElseThrow(exMethod::newNotFound);
  }

}
