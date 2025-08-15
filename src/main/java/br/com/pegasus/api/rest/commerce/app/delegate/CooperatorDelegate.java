package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.api.rest.commerce.infra.mapper.PageableMapper;
import br.com.pegasus.api.rest.commerce.infra.util.RestUtil;
import br.com.pegasus.gen.openapi.api.CooperatorApi;
import br.com.pegasus.gen.openapi.api.CooperatorApiDelegate;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Log4j2
@RestController
public class CooperatorDelegate implements CooperatorApiDelegate {

    private final CooperatorMapper cooperatorMapper = new CooperatorMapper();
    private final PageableMapper pageableMapper = new PageableMapper();

    private final CooperatorPort service;

    public CooperatorDelegate( CooperatorPort service){
        this.service = service;
        log.info("CooperatorDelegate");
    }

    @PostConstruct
    public void init() {
        log.info("CooperatorDelegate inicializado");
    }

    @Override
    public CompletableFuture<ResponseEntity<CooperatorPageResponseType>> cooperatorGetPage(Integer page, Integer size) {

        log.info("cooperatorGetPage");
        PageModel inModel = pageableMapper.toModel(page, size);
        PageableModel<CooperatorModel> outModel = service.findPage(inModel);
        CooperatorPageResponseType response = cooperatorMapper.toType(outModel);
        return RestUtil.createReponse(response, HttpStatus.CREATED);
    }

    @Override
    public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorGetOne(Integer id) {

        log.info("cooperatorGetOne");
        CooperatorModel outModel = service.findById(cooperatorMapper.toModel(id));
        CooperatorType response = cooperatorMapper.toType(outModel);
        return RestUtil.createReponse(response, HttpStatus.CREATED);
    }

    @Override
    public CompletableFuture<ResponseEntity<CooperatorType>> cooperatorCreate(CooperatorCreateBodyType cooperatorCreateBodyType) {

        log.info("cooperatorCreate");
        CooperatorModel outModel = service.create(cooperatorMapper.toModel(cooperatorCreateBodyType));
        CooperatorType response = cooperatorMapper.toType(outModel);
        return RestUtil.createReponse(response, HttpStatus.CREATED);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> cooperatorUpdate(Integer id, CooperatorUpdateBodyType cooperatorUpdateBodyType) {

        log.info("cooperatorUpdate");
        CooperatorModel inModel = cooperatorMapper.toModel(id, cooperatorUpdateBodyType);
        service.update(inModel);
        return RestUtil.createReponse(HttpStatus.NO_CONTENT);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> cooperatorDelete(Integer id) {

        log.info("cooperatorDelete");
        service.delete(cooperatorMapper.toModel(id));
        return RestUtil.createReponse(HttpStatus.NO_CONTENT);
    }

}

