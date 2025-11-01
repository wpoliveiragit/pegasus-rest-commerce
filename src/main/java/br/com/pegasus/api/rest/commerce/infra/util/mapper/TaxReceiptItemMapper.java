package br.com.pegasus.api.rest.commerce.infra.util.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.TaxReceiptItemEntity;
import br.com.pegasus.api.rest.commerce.infra.repository.id.TaxReceiptItemId;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemPageResponseType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class TaxReceiptItemMapper {

  private final PageableMapper pageableMapper;

  // TIPE
  public TaxReceiptItemType toType(TaxReceiptItemModel obj) {
    if (obj == null) return null;
    return TaxReceiptItemType.builder()//
        .taxReceiptId(obj.getTaxReceiptId())//
        .productId(obj.getProductId())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public TaxReceiptItemPageResponseType toType(PageableModel<TaxReceiptItemModel> obj) {
    if (obj == null || obj.getList() == null) return null;
    return TaxReceiptItemPageResponseType.builder()//
        .pagination(pageableMapper.toType(obj))//
        .data(obj.getList().stream().map(this::toType).toList())//
        .build();
  }

  public PageModel toModelByPage(Integer page, Integer size) {
    return pageableMapper.toModel(page, size);
  }

  public TaxReceiptItemModel toModelById(Integer productId, Integer taxReceiptId) {
    return TaxReceiptItemModel.builder()//
        .taxReceiptId(taxReceiptId)//
        .productId(productId)//
        .build();
  }

  public TaxReceiptItemModel toModelByTaxReceiptId(Integer taxReceiptId) {
    return TaxReceiptItemModel.builder()//
        .taxReceiptId(taxReceiptId)//
        .build();
  }

  public TaxReceiptItemModel toModel(TaxReceiptItemType obj) {
    if(obj == null) return null;
    return TaxReceiptItemModel.builder()//
        .taxReceiptId(obj.getTaxReceiptId())//
        .productId(obj.getProductId())//
        .quantity(obj.getQuantity())//
        .build();
  }


  public TaxReceiptItemModel toModel(TaxReceiptItemCreateBodyType obj) {
    if(obj == null) return null;
    return TaxReceiptItemModel.builder()//
        .taxReceiptId(obj.getTaxReceiptId())//
        .productId(obj.getProductId())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public PageableModel<TaxReceiptItemModel> toModel(Page<TaxReceiptItemEntity> obj) {
    if(obj == null) return null;
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public TaxReceiptItemModel toModel(TaxReceiptItemEntity obj) {
    if(obj == null || obj.getId() == null) return null;
    return TaxReceiptItemModel.builder()//
        .taxReceiptId(obj.getId().getTaxReceiptId())//
        .productId(obj.getId().getProductId())//
        .quantity(obj.getQuantity())//
        .build();
  }

  public TaxReceiptItemEntity toEntity(TaxReceiptItemModel obj) {
    TaxReceiptItemId id = TaxReceiptItemId.builder()//
        .taxReceiptId(obj.getTaxReceiptId())//
        .productId(obj.getProductId())//
        .build();

    return TaxReceiptItemEntity.builder()//
        .id(id)//
        .quantity(obj.getQuantity())//
        .build();
  }

  public Pageable toEntity(PageModel obj) {
    return pageableMapper.toEntity(obj);
  }
}
