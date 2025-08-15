package br.com.pegasus.api.rest.commerce.domain.port;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CreateCooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.DeleteCooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.FindByIdCooperatorModel;
import org.springframework.stereotype.Service;

@Service
public interface CooperatorPort {

    PageableModel<CooperatorModel> findPage(PageModel inModel);

    CooperatorModel findById(CooperatorModel inModel);

    CooperatorModel create(CooperatorModel inModel);

    void update(CooperatorModel inModel);

    void delete(CooperatorModel inModel);
}
