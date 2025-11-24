package br.com.pegasus.api.rest.commerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
  /** Id do produto */
  private Long id;
  /** Nome do produto */
  private String name;
  /** Preço do produto */
  private Float price;
  /** Quantidade de itens do produto em estoque */
  private Integer quantity;
  /** Momento de adição do item no banco de dados */
  private OffsetDateTime createdAt;
  /** Momento de atualização do item no banco de dados */
  private OffsetDateTime updatedAt;
}
