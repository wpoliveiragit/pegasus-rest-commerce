package br.com.pegasus.api.rest.commerce.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;

import java.util.Optional;

public interface ProductAdaterJPA {
  PageableModel<ProductModel> findAll(RequestModel request);
  Optional<ProductModel> findById(RequestModel request);
  ProductModel create(RequestModel request);
  ProductModel update(RequestModel request);
  void delete(RequestModel request);
  Optional<ProductModel> findByName(RequestModel request);
}