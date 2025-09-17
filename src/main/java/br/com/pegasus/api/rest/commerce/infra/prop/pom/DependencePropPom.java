package br.com.pegasus.api.rest.commerce.infra.prop.pom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DependencePropPom {
  private String groupId;
  private String artifactId;
  private String version;
  private String description;
}


