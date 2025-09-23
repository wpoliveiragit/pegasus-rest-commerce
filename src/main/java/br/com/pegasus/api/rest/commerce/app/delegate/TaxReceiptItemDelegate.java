package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.app.tool.ValidTool;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptItemMapper;
import br.com.pegasus.gen.openapi.api.TaxReceiptItemApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxReceiptItemDelegate implements TaxReceiptItemApiDelegate {

  private final TaxReceiptItemPort service;
  private final TaxReceiptItemMapper mapper;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemPageResponseType>> taxReceiptItemGetPage(Integer page, Integer size) {

    ValidTool.page(page, size);
    //!: ToModel → Service → ToType
    return ResponseTool.ok(mapper.toType(service.findPage(mapper.toModelByPage(page, size))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemGetOne(Integer taxReceiptId, Integer productId) {

    ValidTool.productId(productId);
    ValidTool.taxReceiptId(taxReceiptId);
    //!: ToModel → service → ToType
    return ResponseTool.ok(mapper.toType(service.findById(mapper.toModelById(taxReceiptId, productId))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemPageResponseType>> taxReceiptItemGetPageByTaxReceiptId(Integer taxReceiptId, Integer page, Integer size) {

    ValidTool.page(page, size);
    ValidTool.taxReceiptId(taxReceiptId);
    //!: ToMode → Service → ToType
    return ResponseTool.ok(mapper.toType(service.findPageByTaxReceiptId(mapper.toModelByPage(page, size), mapper.toModelByTaxReceiptId(taxReceiptId))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemCreate(TaxReceiptItemCreateBodyType bodyType) {

    ValidTool.createBody(bodyType);
    //!: ToModel → Service → ToType
    return ResponseTool.created(mapper.toType(service.create(mapper.toModel(bodyType))));
  }

}
