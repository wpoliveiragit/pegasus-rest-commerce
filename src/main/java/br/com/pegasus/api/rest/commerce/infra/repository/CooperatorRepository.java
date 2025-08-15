package br.com.pegasus.api.rest.commerce.infra.repository;

import br.com.pegasus.api.rest.commerce.infra.repository.entity.CooperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CooperatorRepository extends JpaRepository<CooperatorEntity, Integer> {

    Optional<CooperatorEntity> findByDocumentNumber(String documentNumber);
}
