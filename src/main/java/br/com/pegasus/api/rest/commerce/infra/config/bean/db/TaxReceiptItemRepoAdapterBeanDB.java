package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptItemDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptItemMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.TaxReceiptItemRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaxReceiptItemRepoAdapterBeanDB implements TaxReceiptItemDBAdapter {

  private final TaxReceiptItemRepository repo;
  private final TaxReceiptItemMapper mapper;

  @LogAnnot
  @Override
  public PageableModel<TaxReceiptItemModel> findPage(PageModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(inModel))));
  }

  @Override
  public PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel) {
    return MethodUtil.funcionalExecute(//
        () -> mapper.toModel(repo.findByIdTaxReceiptId(inModel.getTaxReceiptId(), mapper.toEntity(inPageModel)))//
    );
  }


  @LogAnnot
  @Override
  public Optional<TaxReceiptItemModel> findById(TaxReceiptItemModel inModel) {
    return MethodUtil.funcionalExecute(() -> repo.findById(mapper.toEntity(inModel).getId()).map(mapper::toModel));
  }

  @LogAnnot
  @Override
  public TaxReceiptItemModel create(TaxReceiptItemModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }
}
