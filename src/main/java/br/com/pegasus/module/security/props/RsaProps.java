package br.com.pegasus.module.security.props;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RsaProps {
  private String publicKey;
  private String privateKey;
}
