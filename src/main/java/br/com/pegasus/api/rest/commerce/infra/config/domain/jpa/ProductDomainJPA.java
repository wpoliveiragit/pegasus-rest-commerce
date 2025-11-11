package br.com.pegasus.api.rest.commerce.infra.config.domain.jpa;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@LogProxyAnnotation("Repository")
@Component
@RequiredArgsConstructor
public class ProductDomainJPA implements ProductDomainAdaterJPA {

  private final ProductMapper mapper;
  private final ProductRepository repo;

  @Override
  public PageableModel<ProductModel> findAll(RequestModel request) {
    return mapper.jpaToService(repo.findAll(mapper.serviceToJpa(request.getPage())));
  }

  @Override
  public Optional<ProductModel> findById(RequestModel request) {
    return repo.findById(request.getProduct().getId()).map(mapper::jpaToService);
  }

  @Override
  public Optional<ProductModel> findByName(RequestModel request) {
    return repo.findByName(request.getProduct().getName()).map(mapper::jpaToService);
  }

  @Override
  public ProductModel create(RequestModel request) {
    return save(request);
  }

  @Override
  public ProductModel update(RequestModel request) {
    return save(request);
  }

  @Override
  public void delete(RequestModel request) {
    repo.delete(mapper.serviceToJpa(request.getProduct()));
  }

  private ProductModel save(RequestModel request) {
    return mapper.jpaToService(repo.save(mapper.serviceToJpa(request.getProduct())));
  }

}