package br.com.pegasus.api.rest.commerce.infra.repository.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaxReceiptItemId  implements Serializable {

    @Column(name = "TAX_RECEIPT_ID")
    private Integer taxReceiptId;

    @Column(name = "PRODUCT_ID")
    private Integer productId;

}
