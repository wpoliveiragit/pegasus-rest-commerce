package br.com.pegasus.api.rest.commerce.infra.util;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;

import java.util.Optional;

public interface CrudRepositoryUtil<T> {
   
    PageableModel<T> findPage(PageModel page);

    Optional<T> findById(T inModel);

    T create(T inModel);

    T update(T inModel);

    void delete(T inModel);

}
