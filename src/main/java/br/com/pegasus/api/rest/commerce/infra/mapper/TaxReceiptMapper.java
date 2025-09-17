package br.com.pegasus.api.rest.commerce.infra.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.TaxReceiptEntity;
import br.com.pegasus.api.rest.commerce.infra.util.CommomMethod;
import br.com.pegasus.gen.openapi.type.TaxReceiptCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaxReceiptMapper {

  private final PageableMapper pageableMapper;

  //DOMAIN
  public PageModel toModelByPage(Integer page, Integer size) {
    return pageableMapper.toModel(page, size);
  }

  public PageableModel<TaxReceiptModel> toModel(Page<TaxReceiptEntity> obj) {
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public TaxReceiptModel toModelById(Integer id) {
    return TaxReceiptModel.builder()
        .cooperatorId(id)
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptType obj) {
    return TaxReceiptModel.builder()
        .cooperatorId(obj.getCooperatorId())
        .date(obj.getDate())
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptEntity obj) {
    return TaxReceiptModel.builder()
        .cooperatorId(obj.getCooperatorId())
        .date(CommomMethod.toOffsetDateTime(obj.getDate()))
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptCreateBodyType obj) {
    return TaxReceiptModel.builder()
        .cooperatorId(obj.getCooperatorId())
        .build();
  }

  // APP
  public TaxReceiptPageResponseType toTypeByPage(PageableModel<TaxReceiptModel> obj) {
    return TaxReceiptPageResponseType.builder()
        .pagination(pageableMapper.toType(obj))
        .data(obj.getList().stream().map(this::toType).toList())
        .build();
  }

  public TaxReceiptType toType(TaxReceiptModel obj) {
    return TaxReceiptType.builder()
        .cooperatorId(obj.getCooperatorId())
        .date(obj.getDate())
        .build();
  }

  // INFRA
  public Pageable toEntity(PageModel obj) {
    return pageableMapper.toEntity(obj);
  }

  public TaxReceiptEntity toEntity(TaxReceiptModel obj) {
    return TaxReceiptEntity.builder()
        .cooperatorId(obj.getCooperatorId())
        .date(obj.getDate().toLocalDateTime())
        .build();
  }

}
