package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.handler.HttpMethodHandler;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.handler.marker.RestControllerLayerMarker;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.gen.openapi.api.ProductApiDelegate;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductPageResponseType;
import br.com.pegasus.gen.openapi.type.ProductType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestControllerLayerMarker("Delegate.Product")
@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

  private final ProductPort productService;
  private final HttpMethodHandler httpMethod;
  private final ProductMapper mapper;

  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> findAllProduct(UUID xTraceId, Integer page, Integer size) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, page, size);
    PageableModel<ProductModel> responseModel = productService.findAll(requestModel);
    ProductPageResponseType responseType = mapper.serviceToDelegate(responseModel);
    return httpMethod.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> findByIdProduct(UUID xTraceId, Long id) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, id);
    ProductModel responseModel = productService.findById(requestModel);
    ProductType responseType = mapper.serviceToDelegate(responseModel);
    return httpMethod.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> createProduct(UUID xTraceId, ProductCreateBodyType productCreateBodyType) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, productCreateBodyType);
    ProductModel responseModel = productService.create(requestModel);
    ProductType responseType = mapper.serviceToDelegate(responseModel);
    return httpMethod.created(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> updateProduct(UUID xTraceId, Long id, ProductUpdateBodyType productUpdateBodyType) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, id, productUpdateBodyType);
    productService.update(requestModel);
    return httpMethod.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> deleteProduct(UUID xTraceId, Long id) {
    RequestModel requestModel = mapper.deleteToService(xTraceId, id);
    productService.delete(requestModel);
    return httpMethod.noContent();
  }

}