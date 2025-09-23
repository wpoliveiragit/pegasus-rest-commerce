package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.enums.ConflictEnum;

//409: conflict
public class ConflictCoreException extends CoreRuntimeException {

  private ConflictCoreException(ConflictEnum type) {
    super(type.getCodeMsg());
  }

  public static ConflictCoreException newExistingElement() {
    return new ConflictCoreException(ConflictEnum.EXISTING_ELEMENT);
  }

  public static ConflictCoreException newExistingName() {
    return new ConflictCoreException(ConflictEnum.EXISTING_NAME);
  }

  public static ConflictCoreException newExistingDocumentNumber() {
    return new ConflictCoreException(ConflictEnum.EXISTING_DOCUMENT_NUMBER);
  }

}
