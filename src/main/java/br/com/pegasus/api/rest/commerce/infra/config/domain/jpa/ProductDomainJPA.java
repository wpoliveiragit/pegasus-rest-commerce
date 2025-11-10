package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;
import br.com.pegasus.api.rest.commerce.infra.handler.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.ProductEntity;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@LogProxyAnnotation("Repository")
@Component
@RequiredArgsConstructor
public class ProductDomainJPA implements ProductDomainAdaterJPA {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(ProductDomainJPA.class);
  private final ProductMapper mapper;
  private final ProductRepository repo;

  @Override
  public PageableModel<ProductModel> findAll(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ FindPage", xTraceId);
    Pageable requestEntity = mapper.serviceToJpa(request.getPage());
    Page<ProductEntity> responseEntity = repo.findAll(requestEntity);
    PageableModel<ProductModel> responseModel = mapper.jpaToService(responseEntity);
//    log.info("[{}] Repository Adapter ⇇ FindPage", xTraceId);
    return responseModel;
  }

  @Override
  public Optional<ProductModel> findById(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ FindById", xTraceId);
    Optional<ProductModel> responseModel = repo.findById(request.getProduct().getId()).map(mapper::jpaToService);
//    log.info("[{}] Repository Adapter ⇇ FindById", xTraceId);
    return responseModel;
  }

  @Override
  public Optional<ProductModel> findByName(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ FindByName", xTraceId);
    Optional<ProductModel> responseModel = repo.findByName(request.getProduct().getName()).map(mapper::jpaToService);
//    log.info("[{}] Repository Adapter ⇇ FindByName", xTraceId);
    return responseModel;
  }

  @Override
  public ProductModel create(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ Create", xTraceId);
    ProductModel responseModel = save(request);
//    log.info("[{}] Repository Adapter ⇇ Create", xTraceId);
    return responseModel;
  }

  @Override
  public ProductModel update(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ Update", xTraceId);
    ProductModel responseModel = save(request);
//    log.info("[{}] Repository Adapter ⇇ Update", xTraceId);
    return responseModel;
  }

  @Override
  public void delete(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ Delete", xTraceId);
    ProductEntity requestEntity = mapper.serviceToJpa(request.getProduct());
    repo.delete(requestEntity);
//    log.info("[{}] Repository Adapter ⇇ Delete", xTraceId);
  }

  private ProductModel save(RequestModel request) {
    String xTraceId = request.getXTraceId();
//    log.info("[{}] Repository Adapter ⇉ Save", xTraceId);
    ProductEntity requestEntity = mapper.serviceToJpa(request.getProduct());
    ProductEntity responseEntity = repo.save(requestEntity);
    ProductModel responseModel = mapper.jpaToService(responseEntity);
//    log.info("[{}] Repository Adapter ⇇ Save", xTraceId);
    return responseModel;
  }

}
