package br.com.pegasus.api.rest.commerce.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertiesModel {
  private int javaVersion;
  private String springVersion;
  private String name;
  private String email;
  private String SAC;
}
