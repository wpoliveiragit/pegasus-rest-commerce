package br.com.pegasus.module.security.props;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimProps {
  @NotBlank
  private String name;
  @NotBlank
  private String value;
}
