package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.MethodApp;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.util.AppMethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.TaxReceiptItemMapper;
import br.com.pegasus.gen.openapi.api.TaxReceiptItemApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemType;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TaxReceiptItemDelegate implements TaxReceiptItemApiDelegate {

  private static final Logger log = LogManager.getLogger(TaxReceiptItemDelegate.class.getSimpleName());

  private final TaxReceiptItemPort service;
  private final TaxReceiptItemMapper mapper;
  private final MethodApp methodApp;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemPageResponseType>> taxReceiptItemGetPage(UUID xRequestId, Integer page, Integer size) {
    log.info("Delegate ⇉ getPage");
    AppMethodUtil.page(page, size);
    PageModel requestModel = mapper.toModelByPage(page, size);
    PageableModel<TaxReceiptItemModel> responseModel = service.findPage(requestModel);
    TaxReceiptItemPageResponseType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ getPage");
    return methodApp.ok(response);
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemGetOne(UUID xRequestId, Integer taxReceiptId, Integer productId) {
    log.info("Delegate ⇉ getOne");
    AppMethodUtil.productId(productId);
    AppMethodUtil.taxReceiptId(taxReceiptId);
    TaxReceiptItemModel requestModel = mapper.toModelById(taxReceiptId, productId);
    TaxReceiptItemModel responseModel = service.findById(requestModel);
    TaxReceiptItemType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ getOne");
    return methodApp.ok(response);
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemPageResponseType>> taxReceiptItemGetPageByTaxReceiptId(UUID xRequestId, Integer taxReceiptId, Integer page, Integer size) {
    log.info("Delegate ⇉ getPageByTaxReceiptId");
    AppMethodUtil.page(page, size);
    AppMethodUtil.taxReceiptId(taxReceiptId);
    PageModel requestPageModel = mapper.toModelByPage(page, size);
    TaxReceiptItemModel requestModel = mapper.toModelByTaxReceiptId(taxReceiptId);
    PageableModel<TaxReceiptItemModel> responseModel = service.findPageByTaxReceiptId(requestPageModel, requestModel);
    TaxReceiptItemPageResponseType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ getPageByTaxReceiptId");
    return methodApp.ok(response);
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemCreate(UUID xRequestId, TaxReceiptItemCreateBodyType bodyType) {
    log.info("Delegate ⇉ create");
    AppMethodUtil.createBody(bodyType);
    TaxReceiptItemModel requestModel = mapper.toModel(bodyType);
    TaxReceiptItemModel responseModel = service.create(requestModel);
    TaxReceiptItemType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ create");
    return methodApp.created(response);
  }

}
