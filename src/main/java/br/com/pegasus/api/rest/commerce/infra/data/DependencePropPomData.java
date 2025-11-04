package br.com.pegasus.api.rest.commerce.infra.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DependencePropPomData {
  private String groupId;
  private String artifactId;
  private String version;
}


