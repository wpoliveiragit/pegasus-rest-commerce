package br.com.pegasus.api.rest.commerce.infra.prop.pom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PomPropPom {
  private List<DependencePropPom> dependencies;
}
