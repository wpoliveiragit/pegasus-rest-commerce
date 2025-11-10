package br.com.pegasus.api.rest.commerce.domain.port;

import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import org.springframework.stereotype.Service;

@Service
public interface ProductPort {

  PageableModel<ProductModel> findAll(RequestModel request);

  ProductModel findById(RequestModel request);

  ProductModel create(RequestModel request);

  void update(RequestModel request);

  void delete(RequestModel request);
}
