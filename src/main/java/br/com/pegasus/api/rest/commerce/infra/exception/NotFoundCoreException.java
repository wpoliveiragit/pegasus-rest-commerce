package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.enums.NotFoundEnum;

//404: not found
public class NotFoundCoreException extends CoreRuntimeException {

  public NotFoundCoreException(NotFoundEnum type) {
    super(type.getCodeMsg());
  }

  public static NotFoundCoreException newElement() {
    return new NotFoundCoreException(NotFoundEnum.ELEMENT);
  }

  public static NotFoundCoreException newCooperator() {
    return new NotFoundCoreException(NotFoundEnum.COOPERATOR);
  }

  public static NotFoundCoreException newTaxReceipt() {
    return new NotFoundCoreException(NotFoundEnum.TAX_RECEIPT);
  }

  public static NotFoundCoreException newProduct() {
    return new NotFoundCoreException(NotFoundEnum.PRODUCT);
  }

}
