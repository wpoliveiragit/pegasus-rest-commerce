package br.com.pegasus.api.rest.commerce.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.model.DataModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;

import java.util.Optional;

public interface ProductDomainAdaterJPA {

  PageableModel<ProductModel> findAll(DataModel request);

  Optional<ProductModel> findById(DataModel request);

  ProductModel create(DataModel request);

  ProductModel update(DataModel request);

  void delete(DataModel request);

  Optional<ProductModel> findByName(DataModel request);

}
