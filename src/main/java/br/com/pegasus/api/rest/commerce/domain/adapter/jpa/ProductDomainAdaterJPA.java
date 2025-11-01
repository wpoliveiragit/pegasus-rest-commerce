package br.com.pegasus.api.rest.commerce.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;

import java.util.Optional;

public interface ProductDomainAdaterJPA {

  PageableModel<ProductModel> findPage(PageModel page);

  Optional<ProductModel> findById(ProductModel inModel);

  ProductModel create(ProductModel inModel);

  ProductModel update(ProductModel inModel);

  void delete(ProductModel inModel);

  Optional<ProductModel> findByName(ProductModel inModel);

}
