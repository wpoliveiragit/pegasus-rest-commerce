package br.com.pegasus.api.rest.commerce.infra.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.CooperatorEntity;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class CooperatorMapper {

  private final PageableMapper pageableMapper;

  public PageModel toModelByPage(Integer page, Integer size) {
    return pageableMapper.toModel(page, size);
  }

  public CooperatorModel toModelById(Integer id) {
    return CooperatorModel.builder().id(id).build();
  }

  public CooperatorType toType(CooperatorModel obj) {
    return CooperatorType.builder().id(obj.getId()).name(obj.getName()).documentNumber(obj.getDocumentNumber()).build();
  }

  public CooperatorPageResponseType toType(PageableModel<CooperatorModel> obj) {
    return CooperatorPageResponseType.builder().pagination(pageableMapper.toType(obj)).data(obj.getList().stream().map(this::toType).toList()).build();
  }

  public CooperatorModel toModel(CooperatorCreateBodyType obj) {
    return CooperatorModel.builder().name(obj.getName()).documentNumber(obj.getDocumentNumber()).build();
  }

  public CooperatorModel toModel(Integer id, CooperatorUpdateBodyType obj) {
    return CooperatorModel.builder().id(id).name(obj.getName()).build();
  }

  public CooperatorModel toModel(CooperatorEntity obj) {
    return CooperatorModel.builder().id(obj.getId()).name(obj.getName()).documentNumber(obj.getDocumentNumber()).build();
  }

  public PageableModel<CooperatorModel> toModel(Page<CooperatorEntity> obj) {
    return pageableMapper.toModel(obj, obj.get().map(this::toModel).toList());
  }

  public CooperatorEntity toEntity(CooperatorModel obj) {
    return CooperatorEntity.builder().id(obj.getId()).name(obj.getName()).documentNumber(obj.getDocumentNumber()).build();
  }

  public Pageable toEntity(PageModel obj) {
    return pageableMapper.toEntity(obj);
  }

}
