package br.com.pegasus.api.rest.commerce.infra.config.bean.db;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductRepoAdapterBeanDB implements ProductDBAdapter {

  private final ProductMapper mapper;
  private final ProductRepository repo;

  @Override
  public PageableModel<ProductModel> findPage(PageModel page) {
    log.info("FindPage ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> mapper.toModel(repo.findAll(mapper.toEntity(page))));
    log.info("FindPage ⇉ FINISHED");
    return response;
  }

  @Override
  public Optional<ProductModel> findById(ProductModel inModel) {
    log.info("FindById ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> repo.findById(inModel.getId()).map(mapper::toModel));
    log.info("FindById ⇉ FINISHED");
    return response;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel inModel) {
    log.info("FindByName ⇉ STARTED");
    var response = MethodUtil.funcionalExecute(() -> repo.findByName(inModel.getName()).map(mapper::toModel));
    log.info("FindByName ⇉ FINISHED");
    return response;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info("Create ⇉ STARTED");
    var response = save(inModel);
    log.info("Create ⇉ FINISHED");
    return response;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info("Update ⇉ STARTED");
    var response = save(inModel);
    log.info("Update ⇉ FINISHED");
    return response;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info("Delete ⇉ STARTED");
    MethodUtil.executeVoid(() -> repo.delete(mapper.toEntity(inModel)));
    log.info("Delete ⇉ FINISHED");
  }

  private ProductModel save(ProductModel inModel) {
    return MethodUtil.funcionalExecute(() -> mapper.toModel(repo.save(mapper.toEntity(inModel))));
  }

}
