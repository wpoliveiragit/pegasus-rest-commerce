package br.com.pegasus.module.security.props;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimProps {
  /**chave da mensagem adicional.*/
  private String name;
  /**Texto da mensagem adicional.*/
  private String value;
}
