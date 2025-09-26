package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptItemDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptItemMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptItemRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxReceiptItemRepoAdapterBeanDB implements TaxReceiptItemDBAdapter {

  private final TaxReceiptItemRepository repo;
  private final TaxReceiptItemMapper mapper;

  @Override
  public PageableModel<TaxReceiptItemModel> findPage(PageModel inModel) {
    log.info("FindPage ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(inModel))));
    log.info("FindPage ⇉ FINISHED");
    return response;
  }

  @Override
  public PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel) {
    log.info("FindPageByTaxReceiptId ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(//
        () -> mapper.toModel(repo.findByIdTaxReceiptId(inModel.getTaxReceiptId(), mapper.toEntity(inPageModel)))//
    );
    log.info("FindPageByTaxReceiptId ⇉ FINISHED");
    return response;
  }

  @Override
  public Optional<TaxReceiptItemModel> findById(TaxReceiptItemModel inModel) {
    log.info("FindById ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel));
    log.info("FindById ⇉ FINISHED");
    return response;
  }

  @Override
  public TaxReceiptItemModel create(TaxReceiptItemModel inModel) {
    log.info("Create ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
    log.info("Create ⇉ FINISHED");
    return response;
  }
}
