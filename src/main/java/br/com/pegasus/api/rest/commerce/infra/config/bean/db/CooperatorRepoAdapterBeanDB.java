package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.CooperatorRepository;
import br.com.pegasus.api.rest.commerce.infra.util.CommomMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CooperatorRepoAdapterBeanDB implements CooperatorDBAdapter {

  private final CooperatorMapper mapper;
  private final CooperatorRepository repo;

  @LogAnnot
  @Override
  public PageableModel<CooperatorModel> findPage(PageModel page) {
    return CommomMethod.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(page))));
  }

  @LogAnnot
  @Override
  public Optional<CooperatorModel> findById(CooperatorModel inModel) {
    return CommomMethod.funcionalExecute(() -> repo.findById(inModel.getId()).map(mapper::toModel));
  }

  @LogAnnot
  @Override
  public Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel) {
    return CommomMethod.funcionalExecute(() -> repo.findByDocumentNumber(inModel.getDocumentNumber()).map(mapper::toModel));
  }

  @LogAnnot
  @Override
  public CooperatorModel create(CooperatorModel inModel) {
    return save(inModel);
  }

  @LogAnnot
  @Override
  public CooperatorModel update(CooperatorModel inModel) {
    return save(inModel);
  }

  @LogAnnot
  @Override
  public void delete(CooperatorModel inModel) {
    CommomMethod.executeVoid(() -> repo.deleteById(inModel.getId()));
  }

  private CooperatorModel save(CooperatorModel inModel) {
    return CommomMethod.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
