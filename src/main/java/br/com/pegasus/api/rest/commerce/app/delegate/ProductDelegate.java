package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.HttpMethodApp;
import br.com.pegasus.api.rest.commerce.infra.handler.annotation.LogProxyAnnotation;
import br.com.pegasus.api.rest.commerce.infra.handler.log.TrackLogHandler;
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

@LogProxyAnnotation("Delegate")
@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

  private final ProductPort service;
  private final ProductMapper mapper;
  private final HttpMethodApp httpMethodApp;
  private final TrackLogHandler requestLog;

  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> findAllProduct(UUID xTraceId, Integer page, Integer size) {
//    requestLog.appendMessageLog("[{}] Delegate ⇉ findAll", xTraceId);
    RequestModel requestModel = mapper.delegateToService(xTraceId, page, size);
    PageableModel<ProductModel> responseModel = service.findAll(requestModel);
    ProductPageResponseType responseType = mapper.serviceToDelegate(responseModel);
//    requestLog.appendMessageLog("[{}] Delegate ⇇ findAll", xTraceId);
    return httpMethodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> findByIdProduct(UUID xTraceId, Long id) {
//    requestLog.appendMessageLog("[{}] Delegate ⇉ findById", xTraceId);
    RequestModel requestModel = mapper.delegateToService(xTraceId, id);
    ProductModel responseModel = service.findById(requestModel);
    ProductType responseType = mapper.serviceToDelegate(responseModel);
//    requestLog.appendMessageLog("[{}] Delegate ⇇ findById", xTraceId);
    return httpMethodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> createProduct(UUID xTraceId, ProductCreateBodyType productCreateBodyType) {
//    requestLog.appendMessageLog("⇉ Delegate.create");
    RequestModel requestModel = mapper.delegateToService(xTraceId, productCreateBodyType);
    ProductModel responseModel = service.create(requestModel);
    ProductType response = mapper.serviceToDelegate(responseModel);
//    requestLog.appendMessageLog("⇇ Delegate.create");
    return httpMethodApp.created(response);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> updateProduct(UUID xTraceId, Long id, ProductUpdateBodyType productUpdateBodyType) {
//    requestLog.appendMessageLog("[{}] Delegate ⇉ update", xTraceId);
    RequestModel requestModel = mapper.delegateToService(xTraceId, id, productUpdateBodyType);
    service.update(requestModel);
//    requestLog.appendMessageLog("[{}] Delegate ⇇ update", xTraceId);
    return httpMethodApp.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> deleteProduct(UUID xTraceId, Long id) {
//    requestLog.appendMessageLog("[{}] Delegate ⇉ delete", xTraceId);
    RequestModel requestModel = mapper.deleteToService(xTraceId, id);
    service.delete(requestModel);
//    requestLog.appendMessageLog("[{}] Delegate ⇇ delete", xTraceId);
    return httpMethodApp.noContent();
  }

}
