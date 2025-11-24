package br.com.pegasus.api.rest.commerce.infra.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.ProductModel;
import br.com.pegasus.api.rest.commerce.domain.model.RequestModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.ProductEntity;
import br.com.pegasus.api.rest.commerce.infra.telemetry.aspect.mark.TelemetryComponentMark;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductPageResponseType;
import br.com.pegasus.gen.openapi.type.ProductType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@TelemetryComponentMark("Mapper.Product")
public class ProductMapper {

  private final PageableMapper pageableMapper;

  public RequestModel delegateToService(UUID xTraceId, Integer page, Integer size) {
    return RequestModel.builder()//
        .xTraceId(xTraceId.toString())//
        .page(pageableMapper.toModel(page, size))//
        .build();
  }

  public RequestModel delegateToService(UUID xTraceId, Long id) {
    return RequestModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(toModel(id))//
        .build();
  }

  public RequestModel deleteToService(UUID xTraceId, Long id) {
    return RequestModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(toModel(id))//
        .build();
  }

  public RequestModel delegateToService(UUID xTraceId, ProductCreateBodyType body) {
    return RequestModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(toModel(body))//
        .build();
  }

  public RequestModel delegateToService(UUID xTraceId, Long id, ProductUpdateBodyType body) {
    return RequestModel.builder()//
        .xTraceId(xTraceId.toString())//
        .product(toModel(id, body))//
        .build();
  }

  public Pageable serviceToJpa(PageModel model) {
    return pageableMapper.toEntity(model);
  }

  public PageableModel<ProductModel> jpaToService(Page<ProductEntity> obj) {
    return pageableMapper.toModel(obj, obj.get().map(this::jpaToService).toList());
  }

  public ProductModel jpaToService(ProductEntity obj) {
    if (obj == null || obj.getPrice() == null) {
      return null;
    }
    return ProductModel.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice().floatValue())//
        .quantity(obj.getQuantity())//
        .createdAt(obj.getCreatedAt().atOffset(ZoneOffset.UTC))//
        .updatedAt(obj.getUpdatedAt().atOffset(ZoneOffset.UTC))//
        .build();
  }

  public ProductType serviceToDelegate(ProductModel obj) {
    if (obj == null) {
      return null;
    }
    return ProductType.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(obj.getPrice())//
        .quantity(obj.getQuantity())//
        .createdAt(obj.getCreatedAt())//
        .updatedAt(obj.getUpdatedAt())//
        .build();
  }

  public ProductPageResponseType serviceToDelegate(PageableModel<ProductModel> obj) {
    if (obj == null || obj.getList() == null) {
      return null;
    }
    return ProductPageResponseType.builder()//
        .pagination(pageableMapper.toType(obj))//
        .data(obj.getList().stream().map(this::serviceToDelegate).toList())//
        .build();
  }

  public ProductEntity serviceToJpa(ProductModel obj) {
    if (obj == null || obj.getPrice() == null) {
      return null;
    }
    return ProductEntity.builder()//
        .id(obj.getId())//
        .name(obj.getName())//
        .price(BigDecimal.valueOf(obj.getPrice()))//
        .quantity(obj.getQuantity())//
        .createdAt(obj.getCreatedAt().toLocalDateTime()).updatedAt(obj.getUpdatedAt().toLocalDateTime()).build();
  }

  private ProductModel toModel(Long id) {
    return ProductModel.builder().id(id).build();
  }

  private ProductModel toModel(Long id, ProductUpdateBodyType body) {
    return ProductModel.builder()//
        .id(id).name(body.getName())//
        .price(body.getPrice())//
        .quantity(body.getQuantity())//
        .build();
  }

  private ProductModel toModel(ProductCreateBodyType type) {
    return ProductModel.builder()//
        .name(type.getName())//
        .price(type.getPrice())//
        .quantity(type.getQuantity())//
        .build();
  }

}
