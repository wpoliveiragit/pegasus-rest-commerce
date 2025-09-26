package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.app.tool.ValidTool;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.api.rest.commerce.infra.vo.CheckLogVO;
import br.com.pegasus.gen.openapi.api.CooperatorApiDelegate;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Log4j2
@RestController
public class CooperatorDelegate implements CooperatorApiDelegate {

  private final CooperatorPort service;
  private final CooperatorMapper mapper;

  public CooperatorDelegate(CooperatorPort service, CooperatorMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Override
  public CompletableFuture<ResponseEntity<CooperatorPageResponseType>> cooperatorGetPage(Integer page, Integer size) {
    var getPagelog = new CheckLogVO(log,"GetPage").addMessage("Started Delegate");
    ValidTool.page(page, size);
    //!: ToModel → Service → ToModel
    var response = ResponseTool.ok(mapper.toType(service.findPage(getPagelog, mapper.toModelByPage(page, size))));
    getPagelog.addMessage("finished Delegate").sendInfo();
    return response;
  }

  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorGetOne(Integer id) {
    log.info("GetOne ⇉ STARTED");
    ValidTool.commonId(id);
    //!: ToModel → Service → ToType
    var response = ResponseTool.ok(mapper.toType(service.findById(mapper.toModelById(id))));
    log.info("GetOne ⇉ FINISHED");
    return response;
  }

  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorCreate(CooperatorCreateBodyType bodyType) {
    log.info("Create ⇉ STARTED");
    ValidTool.createBody(bodyType);
    //!: ToModel → Service → ToType
    var response = ResponseTool.created(mapper.toType(service.create(mapper.toModel(bodyType))));
    log.info("Create ⇉ FINISHED");
    return response;
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorUpdate(Integer id, CooperatorUpdateBodyType bodyType) {
    log.info("Update ⇉ STARTED");
    ValidTool.updateBody(id, bodyType);
    // !: ToModel → Service
    service.update(mapper.toModel(id, bodyType));
    log.info("Update ⇉ FINISHED");
    return ResponseTool.noContent();
  }

  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorDelete(Integer id) {
    log.info("DELETE ⇉ STARTED");
    ValidTool.commonId(id);
    //!: ToModel → Service
    service.delete(mapper.toModelById(id));
    log.info("DELETE ⇉ FINISHED");
    return ResponseTool.noContent();
  }

}

