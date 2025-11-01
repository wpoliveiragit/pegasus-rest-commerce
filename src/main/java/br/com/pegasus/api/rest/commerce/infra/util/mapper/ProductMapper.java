package br.com.pegasus.api.rest.commerce.infra.util.mapper;

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

@Component
@RequiredArgsConstructor
public final class ProductMapper {

  private final PageableMapper pageableMapper;

  public PageModel toModelByPage(Integer page, Integer size) {
    return pageableMapper.toModel(page, size);
  }

  public PageableModel<ProductModel> toModel(Page<ProductEntity> obj) {
    if(obj == null) return null;
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public ProductModel toModelById(Integer id) {
    return ProductModel.builder()//
        .id(id)//
        .build();
  }

  public ProductModel toModel(ProductCreateBodyType obj) {
    if(obj == null) return null;
    return ProductModel.builder()//
        .name(obj.getName())//
        .price(obj.getPrice())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductModel toModel(Integer productId, ProductUpdateBodyType obj) {
    if(obj == null) return null;
    return ProductModel.builder()//
        .id(productId)//
        .name(obj.getName())//
        .price(obj.getPrice())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductModel toModel(ProductEntity obj) {
    if(obj == null || obj.getPrice() == null) return null;
    return ProductModel.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice().floatValue())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductType toType(ProductModel obj) {
    if(obj == null) return null;
    return ProductType.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public ProductPageResponseType toType(PageableModel<ProductModel> obj) {
    if(obj == null || obj.getList() == null) return null;
    return ProductPageResponseType.builder()//
        .pagination(pageableMapper.toType(obj))//
        .data(obj.getList().stream().map(this::toType).toList())//
        .build();
  }

  public ProductEntity toEntity(ProductModel obj) {
    if(obj == null|| obj.getPrice() == null) return null;
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
