package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.app.tool.ValidTool;
import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.ProductMapper;
import br.com.pegasus.gen.openapi.api.ProductApiDelegate;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductPageResponseType;
import br.com.pegasus.gen.openapi.type.ProductType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

  private final ProductPort service;
  private final ProductMapper mapper;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<ProductPageResponseType>> productGetPage(Integer page, Integer size) {

    ValidTool.page(page, size);
    //!: ValidaToModel → Service → ToType
    return ResponseTool.ok(mapper.toType(service.findPage(mapper.toModelByPage(page, size))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<ProductType>> productGetOne(Integer id) {

    ValidTool.commonId(id);
    // !: ToModel → Service → ToType
    return ResponseTool.ok(mapper.toType(service.findById(mapper.toModelById(id))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<ProductType>> productCreate(ProductCreateBodyType bodyType) {

    ValidTool.createBody(bodyType);
    //!: ToModel → Service → ToType
    return ResponseTool.created(mapper.toType(service.create(mapper.toModel(bodyType))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<Void>> productUpdate(Integer id, ProductUpdateBodyType bodyType) {

    ValidTool.updateBody(id, bodyType);
    //!: ToModel → Service
    service.update(mapper.toModel(bodyType));
    return ResponseTool.noContent();
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<Void>> productDelete(Integer id) {

    ValidTool.commonId(id);
    //!: ToModel → Service
    service.delete(mapper.toModelById(id));
    return ResponseTool.noContent();
  }

}
