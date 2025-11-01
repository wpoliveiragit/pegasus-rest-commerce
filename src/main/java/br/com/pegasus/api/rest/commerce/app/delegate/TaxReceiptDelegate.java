package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.MethodApp;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.AppMethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.TaxReceiptMapper;
import br.com.pegasus.gen.openapi.api.TaxReceiptApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptType;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TaxReceiptDelegate implements TaxReceiptApiDelegate {

  private static final Logger log = LogManager.getLogger(TaxReceiptDelegate.class.getSimpleName());

  private final TaxReceiptPort service;
  private final TaxReceiptMapper mapper;
  private final MethodApp methodApp;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptPageResponseType>> taxReceiptGetPage(UUID xRequestId, Integer page, Integer size) {
    log.info("Delegate ⇉ getPage");
    AppMethodUtil.page(page, size);
    PageModel requestModel = mapper.toModelByPage(page, size);
    PageableModel<TaxReceiptModel> responseModel = service.findPage(requestModel);
    TaxReceiptPageResponseType response = mapper.toTypeByPage(responseModel);
    log.info("Delegate ⇇ getPage");
    return methodApp.ok(response);
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptGetOne(UUID xRequestId, Integer id) {
    log.info("Delegate ⇉ getOne");
    AppMethodUtil.commonId(id);
    TaxReceiptModel requestModel = mapper.toModelById(id);
    TaxReceiptModel responseModel = service.findById(requestModel);
    TaxReceiptType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ getOne");
    return methodApp.ok(response);
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptCreate(UUID xRequestId, TaxReceiptCreateBodyType bodyType) {
    log.info("Delegate ⇉ create");
    AppMethodUtil.createBody(bodyType);
    TaxReceiptModel requestModel = mapper.toModel(bodyType);
    TaxReceiptModel responseModel = service.create(requestModel);
    TaxReceiptType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ create");
    return methodApp.created(response);
  }

}
