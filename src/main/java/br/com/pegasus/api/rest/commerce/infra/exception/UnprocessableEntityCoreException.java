package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;

// 422 → requisição valida, mas violam as regras de negócios.
public class UnprocessableEntityCoreException extends CoreRuntimeException {

  public UnprocessableEntityCoreException(CodeMessageVO type) {
    super(type);
  }

}
