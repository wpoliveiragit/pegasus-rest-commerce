package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;

public class UnprocessableCoreException extends CoreRuntimeException {

  // 422 → requisição valida, mas violam as regras de negócios.
  public UnprocessableCoreException(CodeMessageVO type) {
    super(type);
  }

}
