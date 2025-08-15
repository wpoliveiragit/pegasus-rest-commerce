package br.com.pegasus.api.rest.commerce.infra.repository;

import br.com.pegasus.api.rest.commerce.infra.repository.entity.TaxReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxReceiptRepository  extends JpaRepository<TaxReceiptEntity, Integer> {
}
