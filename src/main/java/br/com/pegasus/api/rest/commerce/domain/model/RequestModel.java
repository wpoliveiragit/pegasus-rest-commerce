package br.com.pegasus.api.rest.commerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
  /** Identificado da request */
  private String xTraceId;
  /** Pagina desejada da lista */
  private PageModel page;
  /** Dados ou parte de dados do produto desejado */
  private ProductModel product;
}
