package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.CooperatorDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.repository.CooperatorRepository;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.CooperatorEntity;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.CooperatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CooperatorDomainJPA implements CooperatorDomainAdapterJPA {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(CooperatorDomainJPA.class);

  private final CooperatorMapper mapper;
  private final CooperatorRepository repo;

  @Override
  public PageableModel<CooperatorModel> findPage(PageModel page) {
    log.info("Repository (Adapter) ⇉ FindPage");
    Pageable pageableEntity = mapper.toEntity(page);
    Page<CooperatorEntity> pageEntity = repo.findAll(pageableEntity);
    PageableModel<CooperatorModel> responseModel = mapper.toModel(pageEntity);
    log.info("Repository (Adapter) ⇇ FindPage");
    return responseModel;
  }

  @Override
  public Optional<CooperatorModel> findById(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ FindById");
    Optional<CooperatorModel> responseModel = repo.findById(inModel.getId()).map(mapper::toModel);
    log.info("Repository (Adapter) ⇇ FindById");
    return responseModel;
  }

  @Override
  public Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ FindByDocumentNumber");
    var responseModel = repo.findByDocumentNumber(inModel.getDocumentNumber()).map(mapper::toModel);
    log.info("Repository (Adapter) ⇇ FindByDocumentNumber");
    return responseModel;
  }

  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ Create");
    var responseModel = save(inModel);
    log.info("Repository (Adapter) ⇇ Create");
    return responseModel;
  }

  @Override
  public CooperatorModel update(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ Update");
    var responseModel = save(inModel);
    log.info("Repository (Adapter) ⇇ Udate");
    return responseModel;
  }

  @Override
  public void delete(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ Delete");
    repo.deleteById(inModel.getId());
    log.info("Repository (Adapter) ⇇ Delete");
  }

  private CooperatorModel save(CooperatorModel inModel) {
    log.info("Repository (Adapter) ⇉ Save");
    CooperatorEntity entity = mapper.toEntity(inModel);
    CooperatorEntity save = repo.save(entity);
    CooperatorModel responseModel = mapper.toModel(save);
    log.info("Repository (Adapter) ⇇ Save");
    return responseModel;
  }

}
