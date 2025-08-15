package br.com.pegasus.api.rest.commerce.domain.model.cooperator;

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
public class CooperatorModel {

    private int id;
    private String name;
    private String documentNumber;
}
