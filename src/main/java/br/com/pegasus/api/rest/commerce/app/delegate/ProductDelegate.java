package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.handler.HttpMethodHandler;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryControllerMark;
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

@TelemetryControllerMark("Delegate.Product")
@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

  private final ProductPort service;
  private final ProductMapper mapper;
  private final HttpMethodHandler httpMethod;

  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> findAllProduct(UUID xTraceId, Integer page, Integer size) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, page, size);
    PageableModel<ProductModel> responseModel = service.findAll(requestModel);
    ProductPageResponseType responseType = mapper.serviceToDelegate(responseModel);
    return httpMethod.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> findByIdProduct(UUID xTraceId, Long id) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, id);
    ProductModel responseModel = service.findById(requestModel);
    ProductType responseType = mapper.serviceToDelegate(responseModel);
    return httpMethod.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> createProduct(UUID xTraceId, ProductCreateBodyType productCreateBodyType) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, productCreateBodyType);
    ProductModel responseModel = service.create(requestModel);
    ProductType response = mapper.serviceToDelegate(responseModel);
    return httpMethod.created(response);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> updateProduct(UUID xTraceId, Long id, ProductUpdateBodyType productUpdateBodyType) {
    RequestModel requestModel = mapper.delegateToService(xTraceId, id, productUpdateBodyType);
    service.update(requestModel);
    return httpMethod.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> deleteProduct(UUID xTraceId, Long id) {
    RequestModel requestModel = mapper.deleteToService(xTraceId, id);
    service.delete(requestModel);
    return httpMethod.noContent();
  }

}
