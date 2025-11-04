package br.com.pegasus.api.rest.commerce.domain.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
  private Long id;
  private String name;
  private Float price;
  private Integer quantity;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
