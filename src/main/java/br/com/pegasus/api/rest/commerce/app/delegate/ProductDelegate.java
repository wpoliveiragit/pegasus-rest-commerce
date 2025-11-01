package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.MethodApp;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.util.AppMethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.ProductMapper;
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
  private final MethodApp methodApp;

  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> productGetPage(UUID xRequestId, Integer page, Integer size) {
    log.info("Delegate ⇉ getPage");
    AppMethodUtil.page(page, size);
    PageModel requestModel = mapper.toModelByPage(page, size);
    PageableModel<ProductModel> responseModel = service.findPage(requestModel);
    ProductPageResponseType responseType = mapper.toType(responseModel);
    log.info("Delegate ⇇ getPage");
    return methodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> productGetOne(UUID xRequestId, Integer productId) {
    log.info("Delegate ⇉ getOne");
    AppMethodUtil.commonId(productId);
    ProductModel requestModel = mapper.toModelById(productId);
    ProductModel responseModel = service.findById(requestModel);
    ProductType responseType = mapper.toType(responseModel);
    log.info("Delegate ⇇ getOne");
    return methodApp.ok(responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<ProductType>> productCreate(UUID xRequestId, ProductCreateBodyType productCreateBodyType) {
    log.info("Delegate ⇉ create");
    AppMethodUtil.createBody(productCreateBodyType);
    ProductModel requestModel = mapper.toModel(productCreateBodyType);
    ProductModel responseModel = service.create(requestModel);
    ProductType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ create");
    return methodApp.created(response);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> productUpdate(UUID xRequestId, Integer productId, ProductUpdateBodyType productUpdateBodyType) {
    log.info("Delegate ⇉ update");
    AppMethodUtil.updateBody(productId, productUpdateBodyType);
    ProductModel requestModel = mapper.toModel(productId, productUpdateBodyType);
    service.update(requestModel);
    log.info("Delegate ⇇ update");
    return methodApp.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> productDelete(UUID xRequestId, Integer productId) {
    log.info("Delegate ⇉ delete");
    AppMethodUtil.commonId(productId);
    ProductModel requestModel = mapper.toModelById(productId);
    service.delete(requestModel);
    log.info("Delegate ⇇ delete");
    return methodApp.noContent();
  }

}
