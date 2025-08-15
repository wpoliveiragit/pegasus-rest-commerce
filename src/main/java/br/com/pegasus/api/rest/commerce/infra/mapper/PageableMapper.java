package br.com.pegasus.api.rest.commerce.infra.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.gen.openapi.type.PaginationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public final class PageableMapper {

    public PaginationType toType(@NotNull PageableModel<?> in){
        return PaginationType.builder()
                .page(in.getPage())
                .size(in.getSize())
                .elements(in.getElements())
                .pages(in.getPages())
                .previous(in.isPrevious())
                .next(in.isNext())
                .build();
    }

    public PageModel toModel(Integer page, Integer size) {
        return PageModel.builder()
                .number(page)
                .size(size)
                .build();
    }

    public <T> PageableModel<T> toModel(Page<?> page, List<T> list) {
        return PageableModel.<T>builder()
                .page(page.getNumber())               // número da página atual (0-based)
                .size(page.getSize())                 // tamanho da página
                .elements(page.getTotalElements())    // total de elementos
                .pages(page.getTotalPages())          // total de páginas
                .previous(page.hasPrevious())         // se tem página anterior
                .next(page.hasNext())                 // se tem próxima página
                .list(list)                           // lista convertida
                .build();
    }


    public Pageable toEntity(@NotNull PageModel in){
        return PageRequest.of(in.getNumber(), in.getSize());
    }

}
