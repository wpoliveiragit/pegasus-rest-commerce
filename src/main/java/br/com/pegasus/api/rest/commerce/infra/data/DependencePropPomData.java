package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DependencePropPomData {
  private String groupId;
  private String artifactId;
  private String version;
  private String description;
}


