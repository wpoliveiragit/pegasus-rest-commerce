package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptRepository;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.TaxReceiptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaxReceiptDomainJPA implements TaxReceiptDomainAdapterJPA {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(TaxReceiptDomainJPA.class);
  private final TaxReceiptRepository repo;
  private final TaxReceiptMapper mapper;

  @Override
  public PageableModel<TaxReceiptModel> findPage(PageModel inModel) {
    log.info("Repository Adapter ⇉ FindPage");
    var responseModel = mapper.toModel(repo.findAll(mapper.toEntity(inModel)));
    log.info("Repository Adapter ⇇ FindPage");
    return responseModel;
  }

  @Override
  public Optional<TaxReceiptModel> findById(TaxReceiptModel inModel) {
    log.info("Repository Adapter ⇉ FindById");
    var responseModel = repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel);
    log.info("Repository Adapter ⇇ FindById");
    return responseModel;
  }

  @Override
  public TaxReceiptModel create(TaxReceiptModel inModel) {
    log.info("Repository Adapter ⇉ Create");
    var responseModel = save(inModel);
    log.info("Repository Adapter ⇇ Create");
    return responseModel;
  }

  private TaxReceiptModel save(TaxReceiptModel inModel) {
    log.info("Repository Adapter ⇉ Save");
    TaxReceiptModel responseModel = mapper.toModel(repo.save(mapper.toEntity(inModel)));
    log.info("Repository Adapter ⇇ Save");
    return responseModel;
  }

}
