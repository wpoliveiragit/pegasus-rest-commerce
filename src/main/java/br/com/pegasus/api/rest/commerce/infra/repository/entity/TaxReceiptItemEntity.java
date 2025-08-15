package br.com.pegasus.api.rest.commerce.infra.repository.entity;

import br.com.pegasus.api.rest.commerce.infra.repository.id.TaxReceiptItemId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_TAX_RECEIPT_ITEM")
public class TaxReceiptItemEntity {

    @EmbeddedId
    private TaxReceiptItemId id;

    @Column(name = "QUANTITY")
    private Integer quantity;
}
