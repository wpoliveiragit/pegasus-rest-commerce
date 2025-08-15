package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.port.TaxReceiptPort;
import br.com.pegasus.gen.openapi.api.TaxReceiptApiDelegate;
import br.com.pegasus.gen.openapi.type.TaxReceiptCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptType;
import br.com.pegasus.gen.openapi.type.TaxReceiptUpdateBodyType;
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

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptCreate(TaxReceiptCreateBodyType taxReceiptCreateBodyType) {
        return TaxReceiptApiDelegate.super.taxReceiptCreate(taxReceiptCreateBodyType);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> taxReceiptDelete(Integer id) {
        return TaxReceiptApiDelegate.super.taxReceiptDelete(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptPageResponseType>> taxReceiptGetAllPage(Integer page, Integer size) {
        return TaxReceiptApiDelegate.super.taxReceiptGetAllPage(page, size);
    }

    @Override
    public CompletableFuture<ResponseEntity<TaxReceiptType>> taxReceiptGetOne(Integer id) {
        return TaxReceiptApiDelegate.super.taxReceiptGetOne(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> taxReceiptUpdate(Integer id, TaxReceiptUpdateBodyType taxReceiptUpdateBodyType) {
        return TaxReceiptApiDelegate.super.taxReceiptUpdate(id, taxReceiptUpdateBodyType);
    }
}
