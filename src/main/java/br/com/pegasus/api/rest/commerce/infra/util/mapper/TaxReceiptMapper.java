package br.com.pegasus.api.rest.commerce.infra.util.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.TaxReceiptEntity;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
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
  private final CommonMapper commonMapper;

  public PageModel toModelByPage(Integer page, Integer size) {
    return pageableMapper.toModel(page, size);
  }

  public PageableModel<TaxReceiptModel> toModel(Page<TaxReceiptEntity> obj) {
    if (obj == null) return null;
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public TaxReceiptModel toModelById(Integer id) {
    return TaxReceiptModel.builder()//
        .cooperatorId(id)//
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptType obj) {
    if (obj == null) return null;
    return TaxReceiptModel.builder()//
        .cooperatorId(obj.getCooperatorId())//
        .date(obj.getDate())//
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptEntity obj) {
    if (obj == null) return null;
    return TaxReceiptModel.builder()//
        .cooperatorId(obj.getCooperatorId())//
        .date(commonMapper.toOffsetDateTime(obj.getDate()))//
        .build();
  }

  public TaxReceiptModel toModel(TaxReceiptCreateBodyType obj) {
    if (obj == null) return null;
    return TaxReceiptModel.builder()//
        .cooperatorId(obj.getCooperatorId())//
        .build();
  }

  public TaxReceiptPageResponseType toTypeByPage(PageableModel<TaxReceiptModel> obj) {
    if (obj == null || obj.getList() == null) return null;
    return TaxReceiptPageResponseType.builder()//
        .pagination(pageableMapper.toType(obj))//
        .data(obj.getList().stream().map(this::toType).toList())//
        .build();
  }

  public TaxReceiptType toType(TaxReceiptModel obj) {
    if (obj == null) return null;
    return TaxReceiptType.builder()//
        .cooperatorId(obj.getCooperatorId())//
        .date(obj.getDate())//
        .build();
  }

  public Pageable toEntity(PageModel obj) {
    return pageableMapper.toEntity(obj);
  }

  public TaxReceiptEntity toEntity(TaxReceiptModel obj) {
    if (obj == null || obj.getDate() == null) return null;
    return TaxReceiptEntity.builder()//
        .cooperatorId(obj.getCooperatorId())//
        .date(obj.getDate().toLocalDateTime())//
        .build();
  }

}
