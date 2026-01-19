package br.com.pegasus.module.security.props;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RsaProps {
  @NotBlank
  private String publicKey;
  @NotBlank
  private String privateKey;
}
