package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxReceiptRepoAdapterBeanDB implements TaxReceiptDBAdapter {

  private final TaxReceiptRepository repo;
  private final TaxReceiptMapper mapper;

  @Override
  public PageableModel<TaxReceiptModel> findPage(PageModel inModel) {
    log.info("FindPage ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(inModel))));
    log.info("FindPage ⇉ FINISHED");
    return response;
  }

  @Override
  public Optional<TaxReceiptModel> findById(TaxReceiptModel inModel) {
    log.info("FindById ⇉ STARTED");
    var response = MethodUtil.funcionalExecute( //
        () -> repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel) //
    );
    log.info("FindById ⇉ FINISHED");
    return response;
  }

  @Override
  public TaxReceiptModel create(TaxReceiptModel inModel) {
    log.info("Create ⇉ STARTED");
    var response = save(inModel);
    log.info("Create ⇉ FINISHED");
    return response;
  }

  private TaxReceiptModel save(TaxReceiptModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
