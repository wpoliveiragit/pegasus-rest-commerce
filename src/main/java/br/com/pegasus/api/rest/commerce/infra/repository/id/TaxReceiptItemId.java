package br.com.pegasus.api.rest.commerce.infra.repository.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaxReceiptItemId implements Serializable {

  @Column(name = "TAX_RECEIPT_ID")
  private Integer taxReceiptId;

  @Column(name = "PRODUCT_ID")
  private Integer productId;

}
