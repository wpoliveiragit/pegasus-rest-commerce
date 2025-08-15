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
@AllArgsConstructor
@NoArgsConstructor
public class PageableModel<T> {

    private int page;
    private int size;
    private long elements;
    private int pages;
    private boolean previous;
    private boolean next;

    private List<T> list;
}
