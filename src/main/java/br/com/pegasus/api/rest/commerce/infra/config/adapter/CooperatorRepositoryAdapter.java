package br.com.pegasus.api.rest.commerce.infra.config.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.CooperatorJpaAdapter;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.cooperator.CooperatorModel;
import br.com.pegasus.api.rest.commerce.infra.mapper.CooperatorMapper;
import br.com.pegasus.api.rest.commerce.infra.mapper.PageableMapper;
import br.com.pegasus.api.rest.commerce.infra.repository.CooperatorRepository;
import br.com.pegasus.api.rest.commerce.infra.repository.entity.CooperatorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CooperatorRepositoryAdapter implements CooperatorJpaAdapter {

    private final PageableMapper pageableMapper = new PageableMapper();
    private final CooperatorMapper cooperatorMapper = new CooperatorMapper();

    private final CooperatorRepository repo;

    @Override
    public PageableModel<CooperatorModel> findPage(PageModel page) {
        Page<CooperatorEntity> pageEntity = repo.findAll(pageableMapper.toEntity(page));
        return pageableMapper.toModel(pageEntity, pageEntity.get().map(cooperatorMapper::toModel).toList());
    }

    @Override
    public Optional<CooperatorModel> findById(CooperatorModel inModel) {
        return repo.findById(inModel.getId()).map(cooperatorMapper::toModel);
    }

    @Override
    public Optional<CooperatorModel> findByDocumentNumber(CooperatorModel inModel) {
        return repo.findByDocumentNumber(inModel.getDocumentNumber()).map(cooperatorMapper::toModel);
    }

    @Override
    public CooperatorModel create(CooperatorModel inModel) {
        return save(inModel);
    }

    @Override
    public CooperatorModel update(CooperatorModel inModel) {
        return save(inModel);
    }

    @Override
    public void deleteById(CooperatorModel inModel) {
        repo.deleteById(inModel.getId());
    }

    private CooperatorModel save(CooperatorModel inModel) {
        return cooperatorMapper.toModel(repo.save(cooperatorMapper.toEntity(inModel)));
    }

}
