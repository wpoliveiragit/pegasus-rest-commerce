package br.com.pegasus.api.rest.commerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableModel<T> {
  /** número da página atual (0-based) */
  private int page;
  /** tamanho da página */
  private int size;
  /** total de elementos */
  private long elements;
  /** total de páginas */
  private int pages;
  /** se tem página anterior */
  private boolean previous;
  /** se tem próxima página */
  private boolean next;
  /** lista convertida */
  private List<T> list;
}