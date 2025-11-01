package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptItemDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptItemRepository;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.TaxReceiptItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaxReceiptItemDomainJPA implements TaxReceiptItemDomainAdapterJPA {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(TaxReceiptItemDomainJPA.class);
  private final TaxReceiptItemRepository repo;
  private final TaxReceiptItemMapper mapper;

  @Override
  public PageableModel<TaxReceiptItemModel> findPage(PageModel inModel) {
    log.info("Repository Adapter ⇉ FindPage");
    var responseModel = mapper.toModel(repo.findAll(mapper.toEntity(inModel)));
    log.info("Repository Adapter ⇇ FindPage");
    return responseModel;
  }

  @Override
  public PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel) {
    log.info("Repository Adapter ⇉ FindPageByTaxReceiptId");
    var responseModel = mapper.toModel(repo.findByIdTaxReceiptId(inModel.getTaxReceiptId(), mapper.toEntity(inPageModel)));
    log.info("Repository Adapter ⇇ FindPageByTaxReceiptId");
    return responseModel;
  }

  @Override
  public Optional<TaxReceiptItemModel> findById(TaxReceiptItemModel inModel) {
    log.info("Repository Adapter ⇉ FindById");
    var responseModel = repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel);
    log.info("Repository Adapter ⇇ FindById");
    return responseModel;
  }

  @Override
  public TaxReceiptItemModel create(TaxReceiptItemModel inModel) {
    log.info("Repository Adapter ⇉ Create");
    var responseModel = mapper.toModel(repo.save(mapper.toEntity(inModel)));
    log.info("Repository Adapter ⇇ Create");
    return responseModel;
  }
}
