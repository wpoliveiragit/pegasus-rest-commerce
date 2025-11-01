package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDomainJPA implements ProductDomainAdaterJPA {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(ProductDomainJPA.class);
  private final ProductMapper mapper;
  private final ProductRepository repo;

  @Override
  public PageableModel<ProductModel> findPage(PageModel page) {
    log.info("Repository Adapter ⇉ FindPage");
    PageableModel<ProductModel> response = mapper.toModel(repo.findAll(mapper.toEntity(page)));
    log.info("Repository Adapter ⇇ FindPage");
    return response;
  }

  @Override
  public Optional<ProductModel> findById(ProductModel inModel) {
    log.info("Repository Adapter ⇉ FindById");
    Optional<ProductModel> response = repo.findById(inModel.getId()).map(mapper::toModel);
    log.info("Repository Adapter ⇇ FindById");
    return response;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel inModel) {
    log.info("Repository Adapter ⇉ FindByName");
    Optional<ProductModel> response = repo.findByName(inModel.getName()).map(mapper::toModel);
    log.info("Repository Adapter ⇇ FindByName");
    return response;
  }

  @Override
  public ProductModel create(ProductModel inModel) {
    log.info("Repository Adapter ⇉ Create");
    ProductModel response = save(inModel);
    log.info("Repository Adapter ⇇ Create");
    return response;
  }

  @Override
  public ProductModel update(ProductModel inModel) {
    log.info("Repository Adapter ⇉ Update");
    ProductModel response = save(inModel);
    log.info("Repository Adapter ⇇ Update");
    return response;
  }

  @Override
  public void delete(ProductModel inModel) {
    log.info("Repository Adapter ⇉ Delete");
    repo.delete(mapper.toEntity(inModel));
    log.info("Repository Adapter ⇇ Delete");
  }

  private ProductModel save(ProductModel inModel) {
    log.info("Repository Adapter ⇉ Save");
    ProductModel response = mapper.toModel(repo.save(mapper.toEntity(inModel)));
    log.info("Repository Adapter ⇇ Save");
    return response;
  }

}
