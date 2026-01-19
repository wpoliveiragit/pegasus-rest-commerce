package br.com.pegasus.module.security.props;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimProps {

  /** chave da mensagem adicional. */
  @NotBlank
  private String name;

  /** Texto da mensagem adicional. */
  @NotBlank
  private String value;
}
