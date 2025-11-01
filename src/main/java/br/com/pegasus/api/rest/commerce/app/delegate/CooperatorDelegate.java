package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.config.app.MethodApp;
import br.com.pegasus.api.rest.commerce.infra.log.AppBaseLog;
import br.com.pegasus.api.rest.commerce.infra.log.AppFactoryLog;
import br.com.pegasus.api.rest.commerce.infra.util.AppMethodUtil;
import br.com.pegasus.api.rest.commerce.infra.util.mapper.CooperatorMapper;
import br.com.pegasus.gen.openapi.api.CooperatorApiDelegate;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class CooperatorDelegate implements CooperatorApiDelegate {

  private static final AppBaseLog log = AppFactoryLog.getCommonLog(CooperatorDelegate.class);

  private final CooperatorPort service;
  private final CooperatorMapper mapper;
  private final MethodApp methodApp;

  @Override
  public CompletableFuture<ResponseEntity<CooperatorPageResponseType>> cooperatorGetPage(UUID xRequestId, Integer page, Integer size) {
    log.info("Delegate ⇉ getPage");
    AppMethodUtil.page(page, size);
    PageModel requestModel = mapper.toModelDataPage(page, size);
    PageableModel<CooperatorModel> responseModel = service.findPage(requestModel);
    CooperatorPageResponseType responseType = mapper.toType(responseModel);
    log.info("Delegate ⇇ getPage");
    return methodApp.createReponse(HttpStatus.OK, responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorGetOne(UUID xRequestId, Integer id) {
    log.info("Delegate ⇉ getOne");
    AppMethodUtil.commonId(id);
    CooperatorModel requestModel = mapper.toModelById(id);
    CooperatorModel responseModel = service.findById(requestModel);
    CooperatorType responseType = mapper.toType(responseModel);
    log.info("Delegate ⇇ getOne");
    return methodApp.createReponse(HttpStatus.OK, responseType);
  }

  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorCreate(UUID xRequestId, CooperatorCreateBodyType bodyType) {
    log.info("Delegate ⇉ create");
    AppMethodUtil.createBody(bodyType);
    CooperatorModel requestModel = mapper.toModel(bodyType);
    CooperatorModel responseModel = service.create(requestModel);
    CooperatorType response = mapper.toType(responseModel);
    log.info("Delegate ⇇ create");
    return methodApp.createReponse(HttpStatus.CREATED, response);
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorUpdate(UUID xRequestId, Integer id, CooperatorUpdateBodyType bodyType) {
    log.info("Delegate ⇉ update");
    AppMethodUtil.updateBody(id, bodyType);
    CooperatorModel requestModel = mapper.toModel(id, bodyType);
    service.update(requestModel);
    log.info("Delegate ⇇ update");
    return methodApp.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorDelete(UUID xRequestId, Integer id) {
    log.info("Delegate ⇉ delete");
    AppMethodUtil.commonId(id);
    CooperatorModel requestModel = mapper.toModelById(id);
    service.delete(requestModel);
    log.info("Delegate ⇇ delete");
    return methodApp.noContent();
  }

}
