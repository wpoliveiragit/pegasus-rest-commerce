package br.com.pegasus.api.rest.commerce.domain.port;

import br.com.pegasus.api.rest.commerce.domain.model.DataModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import org.springframework.stereotype.Service;

@Service
public interface ProductPort {

  PageableModel<ProductModel> findAll(DataModel request);

  ProductModel findById(DataModel request);

  ProductModel create(DataModel request);

  void update(DataModel request);

  void delete(DataModel request);
}
