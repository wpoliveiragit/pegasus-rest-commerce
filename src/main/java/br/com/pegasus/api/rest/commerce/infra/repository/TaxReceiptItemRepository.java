package br.com.pegasus.api.rest.commerce.infra.repository;

import br.com.pegasus.api.rest.commerce.infra.repository.entity.TaxReceiptItemEntity;
import br.com.pegasus.api.rest.commerce.infra.repository.id.TaxReceiptItemId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxReceiptItemRepository extends JpaRepository<TaxReceiptItemEntity, TaxReceiptItemId> {
  Page<TaxReceiptItemEntity> findByIdTaxReceiptId(Integer taxReceiptId, Pageable pageable);
}
