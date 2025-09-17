package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.app.tool.ResponseTool;
import br.com.pegasus.api.rest.commerce.app.tool.ValidTool;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.handler.annot.LogAnnot;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.gen.openapi.api.CooperatorApiDelegate;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CooperatorDelegate implements CooperatorApiDelegate {

  private final CooperatorPort service;
  private final CooperatorMapper mapper;

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<CooperatorPageResponseType>> cooperatorGetPage(
      Integer page, Integer size) {

    ValidTool.page(page, size);
    //!: ToModel → Service → ToModel
    return ResponseTool.ok(mapper.toType(service.findPage(mapper.toModelByPage(page, size))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorGetOne(Integer id) {

    ValidTool.commonId(id);
    //!: ToModel → Service → ToType
    return ResponseTool.ok(mapper.toType(service.findById(mapper.toModelById(id))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorCreate(
      CooperatorCreateBodyType bodyType) {

    ValidTool.createBody(bodyType);
    //!: ToModel → Service → ToType
    return ResponseTool.created(mapper.toType(service.create(mapper.toModel(bodyType))));
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorUpdate(Integer id, CooperatorUpdateBodyType bodyType) {

    ValidTool.updateBody(id, bodyType);
    // !: ToModel → Service
    service.update(mapper.toModel(id, bodyType));
    return ResponseTool.noContent();
  }

  @LogAnnot
  @Override
  public CompletableFuture<ResponseEntity<Void>> cooperatorDelete(Integer id) {

    ValidTool.commonId(id);
    //!: ToModel → Service
    service.delete(mapper.toModelById(id));
    return ResponseTool.noContent();
  }

}

