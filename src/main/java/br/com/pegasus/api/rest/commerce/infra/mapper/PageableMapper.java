package br.com.pegasus.api.rest.commerce.infra.mapper;

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

  public PaginationType toType(PageableModel<?> model) {
    if (model == null) {
      return null;
    }
    return PaginationType.builder()
        .page(model.getPage())
        .size(model.getSize())
        .elements(model.getElements())
        .pages(model.getPages())
        .previous(model.isPrevious())
        .next(model.isNext())
        .build();
  }

  public PageModel toModel(Integer page, Integer size) {
    return PageModel.builder()
        .number(page)
        .size(size)
        .build();
  }

  public <T> PageableModel<T> toModel(Page<?> page, List<T> list) {
    if (page == null) {
      return null;
    }
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

  public Pageable toEntity(PageModel model) {
    if (model == null) {
      return null;
    }
    return PageRequest.of(model.getNumber(), model.getSize());
  }

}
