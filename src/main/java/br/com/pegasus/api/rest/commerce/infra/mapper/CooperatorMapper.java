package br.com.pegasus.api.rest.commerce.infra.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.FindByIdCooperatorModel;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.CooperatorEntity;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorPageResponseType;
import br.com.pegasus.gen.openapi.type.CooperatorType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;

public final class CooperatorMapper {

    private final PageableMapper pageableMapper = new PageableMapper();

    public CooperatorModel toModel(Integer id) {
        return CooperatorModel.builder()
                .id(id)
                .build();
    }

    public CooperatorType toType(CooperatorModel obj) {
        return (obj == null)
                ? null
                : CooperatorType.builder()
                .id(obj.getId())
                .name(obj.getName())
                .documentNumber(obj.getDocumentNumber())
                .build();
    }

    public CooperatorPageResponseType toType(PageableModel<CooperatorModel> obj) {
        return (obj == null)
                ? null
                : CooperatorPageResponseType.builder()
                .data(obj.getList().stream().map(this::toType).toList())
                .pagination(pageableMapper.toType(obj))
                .build();
    }

    public CooperatorModel toModel(CooperatorCreateBodyType obj) {
        return (obj == null)
                ? null
                : CooperatorModel.builder()
                .name(obj.getName())
                .documentNumber(obj.getDocumentNumber())
                .build();
    }

    public CooperatorModel toModel(Integer id, CooperatorUpdateBodyType obj) {
        return (obj == null)
                ? null
                : CooperatorModel.builder()
                .id(id)
                .name(obj.getName())
                .build();
    }

    public CooperatorModel toModel(CooperatorEntity obj) {
        return (obj == null)
                ? null
                : CooperatorModel.builder()
                .id(obj.getId())
                .name(obj.getName())
                .documentNumber(obj.getDocumentNumber())
                .build();
    }

    public CooperatorEntity toEntity(CooperatorModel obj) {
        return (obj == null)
                ? null
                : CooperatorEntity.builder()
                .id(obj.getId())
                .name(obj.getName())
                .documentNumber(obj.getDocumentNumber())
                .build();
    }

}
