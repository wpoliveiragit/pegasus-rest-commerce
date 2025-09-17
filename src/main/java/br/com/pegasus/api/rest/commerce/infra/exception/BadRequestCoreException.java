package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.enums.BadRequestEnum;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;

public class BadRequestCoreException extends CoreRuntimeException {

  //400: Bad request
  public BadRequestCoreException(CodeMessageVO type) {
    super(type);
  }

  public BadRequestCoreException(BadRequestEnum type) {
    super(type.getCodeMsg());
  }

  public static BadRequestCoreException newDocumentNumber() {
    return new BadRequestCoreException(BadRequestEnum.DOCUMENT_NUMBER);
  }

  public static BadRequestCoreException newPrice() {
    return new BadRequestCoreException(BadRequestEnum.PRICE);
  }

  public static BadRequestCoreException newQuantity() {
    return new BadRequestCoreException(BadRequestEnum.QUANTITY);
  }

}