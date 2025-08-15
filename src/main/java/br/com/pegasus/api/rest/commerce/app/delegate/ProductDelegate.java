package br.com.pegasus.api.rest.commerce.app.delegate;

import br.com.pegasus.api.rest.commerce.domain.port.ProductPort;
import br.com.pegasus.gen.openapi.api.ProductApiDelegate;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductPageResponseType;
import br.com.pegasus.gen.openapi.type.ProductType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductDelegate implements ProductApiDelegate {

    private final ProductPort service;

    @Override
    public CompletableFuture<ResponseEntity<ProductType>> productCreate(ProductCreateBodyType productCreateBodyType) {
        return ProductApiDelegate.super.productCreate(productCreateBodyType);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> productDelete(Integer id) {
        return ProductApiDelegate.super.productDelete(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductPageResponseType>> productGetAllPage(Integer page, Integer size) {
        return ProductApiDelegate.super.productGetAllPage(page, size);
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductType>> productGetOne(Integer id) {
        return ProductApiDelegate.super.productGetOne(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> productUpdate(Integer id, ProductUpdateBodyType productUpdateBodyType) {
        return ProductApiDelegate.super.productUpdate(id, productUpdateBodyType);
    }
}
