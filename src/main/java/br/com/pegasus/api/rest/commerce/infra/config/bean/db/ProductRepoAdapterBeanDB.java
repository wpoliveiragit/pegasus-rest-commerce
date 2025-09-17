package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.util.CommomMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepoAdapterBeanDB implements ProductDBAdapter {

  private final ProductMapper mapper;
  private final ProductRepository repo;

  @LogAnnot
  @Override
  public PageableModel<ProductModel> findPage(PageModel page) {
    return CommomMethod.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(page))));
  }

  @LogAnnot
  @Override
  public Optional<ProductModel> findById(ProductModel inModel) {
    return CommomMethod.funcionalExecute(() -> repo.findById(inModel.getId()).map(mapper::toModel));
  }

  @LogAnnot
  @Override
  public Optional<ProductModel> findByName(ProductModel inModel) {
    return CommomMethod.funcionalExecute(() -> repo.findByName(inModel.getName()).map(mapper::toModel));
  }

  @LogAnnot
  @Override
  public ProductModel create(ProductModel inModel) {
    return save(inModel);
  }

  @LogAnnot
  @Override
  public ProductModel update(ProductModel inModel) {
    return save(inModel);
  }

  @LogAnnot
  @Override
  public void delete(ProductModel inModel) {
    CommomMethod.executeVoid(() -> repo.delete(mapper.toEntity(inModel)));
  }

  private ProductModel save(ProductModel inModel) {
    return CommomMethod.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
