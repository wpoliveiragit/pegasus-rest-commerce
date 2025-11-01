package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DependencePropPomData {
  private String groupId;
  private String artifactId;
  private String version;
  private String description;
}


