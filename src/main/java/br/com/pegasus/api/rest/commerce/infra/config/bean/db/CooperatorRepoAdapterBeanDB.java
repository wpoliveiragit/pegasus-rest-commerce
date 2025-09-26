package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.CooperatorRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CheckLogVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class CooperatorRepoAdapterBeanDB implements CooperatorDBAdapter {

  private final CooperatorMapper mapper;
  private final CooperatorRepository repo;

  @Override
  public PageableModel<CooperatorModel> findPage(CheckLogVO getPagelog, PageModel page) {
    getPagelog.addMessage("Started FindPage");
    var response = MethodUtil.funcionalExecute( //
        () -> mapper.toModel(repo.findAll(mapper.toEntity(page))) //
    );
    getPagelog.addMessage("Finished FindPage");
    return response;
  }

  @Override
  public Optional<CooperatorModel> findById(CooperatorModel inModel) {
    log.info("FindById ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(//
        () -> repo.findById(inModel.getId()).map(mapper::toModel)//
    );
    log.info("FindById ⇉ FINISHED");
    return response;
  }

  @Override
  public Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel) {
    log.info("FindByDocumentNumber ⇉ STARTED");
    var response = MethodUtil.funcionalExecute( //
        () -> repo.findByDocumentNumber(inModel.getDocumentNumber()).map(mapper::toModel) //
    );
    log.info("FindByDocumentNumber ⇉ FINISHED");
    return response;
  }

  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    log.info("Create ⇉ STARTED");
    var response = save(inModel);
    log.info("Create ⇉ FINISHED");
    return response;
  }

  @Override
  public CooperatorModel update(CooperatorModel inModel) {
    log.info("Update ⇉ STARTED");
    var response = save(inModel);
    log.info("Update ⇉ FINISHED");
    return response;
  }

  @Override
  public void delete(CooperatorModel inModel) {
    log.info("delete ⇉ STARTED");
    MethodUtil.executeVoid(() -> repo.deleteById(inModel.getId()));
    log.info("delete ⇉ FINISHED");
  }

  private CooperatorModel save(CooperatorModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
