package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.app.tool.ValidTool;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.TaxReceiptMapper;
import br.com.pegasus.gen.openapi.api.TaxReceiptApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxReceiptDelegate implements TaxReceiptApiDelegate {

  private final TaxReceiptPort service;
  private final TaxReceiptMapper mapper;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptPageResponseType>> taxReceiptGetPage(
      Integer page, Integer size) {

    ValidTool.page(page, size);
    //!: ToMode → Service → ToType
    return ResponseTool.ok(mapper.toTypeByPage(service.findPage(mapper.toModelByPage(page, size))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptGetOne(Integer id) {

    ValidTool.commonId(id);
    //!: ToModel → service → ToType retorno
    return ResponseTool.ok(mapper.toType(service.findById(mapper.toModelById(id))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptCreate(
      TaxReceiptCreateBodyType bodyType) {

    ValidTool.createBody(bodyType);
    //!: ToModel → Service → ToType
    return ResponseTool.created(mapper.toType(service.create(mapper.toModel(bodyType))));
  }

}
