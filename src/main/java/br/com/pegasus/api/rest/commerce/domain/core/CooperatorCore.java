package br.com.pegasus.api.rest.commerce.domain.core;

import br.com.pegasus.api.rest.commerce.domain.adapter.CooperatorJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.port.CooperatorPort;
import br.com.pegasus.api.rest.commerce.infra.exception.ConflictException;
import br.com.pegasus.api.rest.commerce.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CooperatorCore implements CooperatorPort {

    private final CooperatorJpaAdapter coopJpa;
    private final CommonMethodCore method = new CommonMethodCore();

    @Override
    public PageableModel<CooperatorModel> findPage(PageModel inModel) {
        method.valid(inModel.getNumber(),"Numero da pagina não enviado");
        method.valid(inModel.getSize(),"Tamanho da pagina não enviado");

        return coopJpa.findPage(inModel);
    }

    @Override
    public CooperatorModel findById(CooperatorModel inModel) {
        method.valid(inModel.getId(),"Id não enviado");

        return coopJpa.findById(inModel)
                .orElseThrow(() -> new NotFoundException("Entidade não encontrada!"));
    }

    @Override
    public CooperatorModel create(CooperatorModel inModel) {
        /* Usar os métodos do Optional deixa mais claro as regras de negocio, porem menos performatico */
        coopJpa.findByDocumentNumber(inModel).ifPresent(obj -> {
            throw new ConflictException("Entidade existente.");
        });
        return coopJpa.create(inModel);
    }

    @Override
    public void update(final CooperatorModel inModel) {
        method.valid(inModel.getId(),"Id não enviado");
        findById(inModel).setName(inModel.getName());
    }

    @Override
    public void delete(CooperatorModel inModel) {
        method.valid(inModel.getId(),"Id não enviado");
        coopJpa.deleteById(findById(inModel));
    }

}
