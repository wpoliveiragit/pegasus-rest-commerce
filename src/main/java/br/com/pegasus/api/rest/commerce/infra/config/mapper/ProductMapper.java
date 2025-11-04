package br.com.pegasus.api.rest.commerce.infra.config.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.DataModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.ProductEntity;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductPageResponseType;
import br.com.pegasus.gen.openapi.type.ProductType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public final class ProductMapper {

  private final PageableMapper pageableMapper;

  public DataModel findAlltoModel(UUID xTraceId, Integer page, Integer size) {
    return DataModel.builder()//
        .xTraceId(xTraceId.toString())//
        .page(pageableMapper.toModel(page, size))//
        .build();
  }

  public DataModel findByIdToModel(UUID xTraceId, Long id) {
    ProductModel product = ProductModel.builder()//
        .id(id)//
        .build();//
    return DataModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(product)//
        .build();
  }

  public DataModel deleteModel(UUID xTraceId, Long id) {
    ProductModel product = ProductModel.builder().id(id).build();
    return DataModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(product)//
        .build();
  }

  public DataModel createToModel(UUID xTraceId, ProductCreateBodyType body) {
    ProductModel product = ProductModel.builder()//
        .name(body.getName())//
        .price(body.getPrice())//
        .quantity(body.getQuantity())//
        .build();
    return DataModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(product)//
        .build();
  }

  public DataModel updateToModel(UUID xTraceId, Long id, ProductUpdateBodyType body) {
    ProductModel product = ProductModel.builder()//
        .id(id)//
        .name(body.getName())//
        .price(body.getPrice())//
        .quantity(body.getQuantity())//
        .build();
    return DataModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(product)//
        .build();
  }

  public PageableModel<ProductModel> toModel(Page<ProductEntity> obj) {
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public ProductModel toModel(ProductEntity obj) {
    if (obj == null || obj.getPrice() == null) return null;
    return ProductModel.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice().floatValue())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductType toType(ProductModel obj) {
    if (obj == null) return null;
    return ProductType.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductPageResponseType toType(PageableModel<ProductModel> obj) {
    if (obj == null || obj.getList() == null) return null;
    return ProductPageResponseType.builder()//
        .pagination(pageableMapper.toType(obj))//
        .data(obj.getList().stream().map(this::toType).toList())//
        .build();
  }

  public ProductEntity toEntity(ProductModel obj) {
    if (obj == null || obj.getPrice() == null) return null;
    return ProductEntity.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(BigDecimal.valueOf(obj.getPrice()))//
        .quantity(obj.getQuantity())//
        .build();
  }

  public Pageable toEntity(PageModel obj) {
    return pageableMapper.toEntity(obj);
  }
}
