package br.com.pegasus.api.rest.commerce.domain.model.prop;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MetadataPropModel {
  private String key;
  private String value;
  private String content;
}
