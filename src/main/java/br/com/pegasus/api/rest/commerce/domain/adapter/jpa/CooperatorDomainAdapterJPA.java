package br.com.pegasus.api.rest.commerce.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;

import java.util.Optional;

public interface CooperatorDomainAdapterJPA {

  PageableModel<CooperatorModel> findPage(PageModel page);

  Optional<CooperatorModel> findById(CooperatorModel inModel);

  CooperatorModel create(CooperatorModel inModel);

  CooperatorModel update(CooperatorModel inModel);

  void delete(CooperatorModel inModel);

  Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel);

}
