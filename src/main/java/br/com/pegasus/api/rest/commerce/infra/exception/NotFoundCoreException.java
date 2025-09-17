package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.enums.NotFoundEnum;

public class NotFoundCoreException extends CoreRuntimeException {

  //404: not found
  public NotFoundCoreException(NotFoundEnum type) {
    super(type.getCodeMsg());
  }

  public static NotFoundCoreException newElement() {
    return new NotFoundCoreException(NotFoundEnum.ELEMENT);
  }

  public static NotFoundCoreException newCooperator() {
    return new NotFoundCoreException(NotFoundEnum.COOPERATOR);
  }

}
