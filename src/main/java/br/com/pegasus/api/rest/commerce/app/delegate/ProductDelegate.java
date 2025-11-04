package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.DataModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.HttpMethodApp;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.config.mapper.ProductMapper;
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

@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(ProductDelegate.class);

  private final ProductPort service;
  private final ProductMapper mapper;
  private final HttpMethodApp httpMethodApp;

  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> findAllProduct(UUID xTraceId, Integer page, Integer size) {
    log.info("[{}] Delegate ⇉ findAll", xTraceId);
    DataModel dataModel = mapper.findAlltoModel(xTraceId, page, size);
    PageableModel<ProductModel> responseModel = service.findAll(dataModel);
    ProductPageResponseType responseType = mapper.toType(responseModel);
    log.info("[{}] Delegate ⇇ findAll", xTraceId);
    return httpMethodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> findByIdProduct(UUID xTraceId, Long id) {
    log.info("[{}] Delegate ⇉ findById", xTraceId);
    DataModel dataModel = mapper.findByIdToModel(xTraceId, id);
    ProductModel responseModel = service.findById(dataModel);
    ProductType responseType = mapper.toType(responseModel);
    log.info("[{}] Delegate ⇇ findById", xTraceId);
    return httpMethodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> createProduct(UUID xTraceId, ProductCreateBodyType productCreateBodyType) {
    log.info("[{}] Delegate ⇉ create", xTraceId);
    DataModel dataModel = mapper.createToModel(xTraceId, productCreateBodyType);
    ProductModel responseModel = service.create(dataModel);
    ProductType response = mapper.toType(responseModel);
    log.info("[{}] Delegate ⇇ create", xTraceId);
    return httpMethodApp.created(response);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> updateProduct(UUID xTraceId, Long id, ProductUpdateBodyType productUpdateBodyType) {
    log.info("[{}] Delegate ⇉ update", xTraceId);
    DataModel dataModel = mapper.updateToModel(xTraceId, id, productUpdateBodyType);
    service.update(dataModel);
    log.info("[{}] Delegate ⇇ update", xTraceId);
    return httpMethodApp.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> deleteProduct(UUID xTraceId, Long id) {
    log.info("[{}] Delegate ⇉ delete", xTraceId);
    DataModel requestModel = mapper.deleteModel(xTraceId, id);
    service.delete(requestModel);
    log.info("[{}] Delegate ⇇ delete", xTraceId);
    return httpMethodApp.noContent();
  }

}
