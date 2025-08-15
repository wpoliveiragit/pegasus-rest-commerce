package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;

import java.util.Optional;

public interface CooperatorJpaAdapter {

    PageableModel<CooperatorModel> findPage(PageModel page);

    Optional<CooperatorModel> findById(CooperatorModel inModel);

    Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel);

    CooperatorModel create(CooperatorModel inModel);

    CooperatorModel update(CooperatorModel inModel);

    void deleteById(CooperatorModel inModel);
}
