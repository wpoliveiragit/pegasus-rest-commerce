package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaxReceiptRepoAdapterBeanDB implements TaxReceiptDBAdapter {

  private final TaxReceiptRepository repo;
  private final TaxReceiptMapper mapper;

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptModel> findPage(PageModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(inModel))));
  }

  @LogAnnot
  @Override
  public Optional<TaxReceiptModel> findById(TaxReceiptModel inModel) {
    return MethodUtil.funcionalExecute( //
        () -> repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel) //
    );
  }

  @LogAnnot
  @Override
  public TaxReceiptModel create(TaxReceiptModel inModel) {
    return save(inModel);
  }

  private TaxReceiptModel save(TaxReceiptModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
