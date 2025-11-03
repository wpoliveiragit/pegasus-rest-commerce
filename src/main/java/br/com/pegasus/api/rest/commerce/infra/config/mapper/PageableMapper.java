package br.com.pegasus.api.rest.commerce.infra.config.mapper;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.gen.openapi.type.PaginationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PageableMapper {

  public PaginationType toType(PageableModel<?> obj) {
    if (obj == null) return null;
    return PaginationType.builder()
        .page(obj.getPage())
        .size(obj.getSize())
        .elements(obj.getElements())
        .pages(obj.getPages())
        .previous(obj.isPrevious())
        .next(obj.isNext())
        .build();
  }

  public PageModel toModel(Integer page, Integer size) {
    return PageModel.builder()
        .number(page)
        .size(size)
        .build();
  }

  public <T> PageableModel<T> toModel(Page<?> page, List<T> list) {
    if (page == null) return null;
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

  public Pageable toEntity(PageModel obj) {
    if (obj == null) return null;
    return PageRequest.of(obj.getNumber(), obj.getSize());
  }

}
