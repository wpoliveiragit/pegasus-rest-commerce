package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptItemPort;
import br.com.pegasus.api.rest.commerce.infra.util.RestUtil;
import br.com.pegasus.gen.openapi.api.TaxReceiptItemApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaxReceiptItemDelegate implements TaxReceiptItemApiDelegate {

    private final TaxReceiptItemPort service;

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemCreate(TaxReceiptItemCreateBodyType taxReceiptItemCreateBodyType) {
        return RestUtil.createReponse(TaxReceiptItemType.builder().build(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<TaxReceiptItemType>>> taxReceiptItemGetAllByTaxReceoptIdPage(Integer taxReceiptId, Integer page, Integer size) {
        return RestUtil.createReponse(new ArrayList<TaxReceiptItemType>(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptItemPageResponseType>> taxReceiptItemGetAllPage(Integer page, Integer size) {
        return RestUtil.createReponse(TaxReceiptItemPageResponseType.builder().build(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptItemType>> taxReceiptItemGetOne(Integer id) {
        return RestUtil.createReponse(TaxReceiptItemType.builder().build(), HttpStatus.NOT_IMPLEMENTED);
    }

}
