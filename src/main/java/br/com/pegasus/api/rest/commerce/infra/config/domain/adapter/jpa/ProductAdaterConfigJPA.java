package br.com.pegasus.api.rest.commerce.infra.config.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.ProductEntity;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@TelemetryComponentMark("JPA.Product")
@Component
@RequiredArgsConstructor
public class ProductAdaterConfigJPA implements ProductAdaterJPA {

  private final ProductMapper mapper;
  private final ProductRepository repo;
  private final TrackLogger trackLog;

  @Override
  public PageableModel<ProductModel> findAll(RequestModel request) {
    PageModel pageModel = request.getPage();
    Pageable pageable = mapper.serviceToJpa(pageModel);
    Page<ProductEntity> productEntityPage = repo.findAll(pageable);
    return mapper.jpaToService(productEntityPage);
  }

  @Override
  public Optional<ProductModel> findById(RequestModel request) {
    Long id = request.getProduct().getId();
    return repo.findById(id).map(mapper::jpaToService);
  }

  @Override
  public Optional<ProductModel> findByName(RequestModel request) {
    String name = request.getProduct().getName();
    return repo.findByName(name).map(mapper::jpaToService);
  }

  @Override
  public ProductModel create(RequestModel request) {
    ProductModel responseModel = save(request);
    trackLog.append("element created successfully");
    return responseModel;
  }

  @Override
  public ProductModel update(RequestModel request) {
    ProductModel responseModel = save(request);
    trackLog.append("element updated successfully");
    return responseModel;
  }

  @Override
  public void delete(RequestModel request) {
    ProductModel productModel = request.getProduct();
    ProductEntity productEntity = mapper.serviceToJpa(productModel);
    repo.delete(productEntity);
  }

  private ProductModel save(RequestModel request) {
    trackLog.append("● JPA.Product(save)");
    ProductModel product = request.getProduct();
    ProductEntity productEntity = mapper.serviceToJpa(product);
    ProductEntity response = repo.save(productEntity);
    ProductModel responseModel = mapper.jpaToService(response);
    trackLog.append("◎ JPA.Product(save)");
    return responseModel;
  }

}