package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.enums.InternalServerErrorEnum;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;

//500: Internal server error
public class InternalServerErrorCoreException extends CoreRuntimeException {

  public InternalServerErrorCoreException(Throwable cause, CodeMessageVO type) {
    super(cause, type);
  }

  public InternalServerErrorCoreException(Throwable cause, InternalServerErrorEnum type) {
    super(cause, type.getCodeMsg());
  }

  public static InternalServerErrorCoreException bdQueryError(Throwable cause) {
    return new InternalServerErrorCoreException(cause, InternalServerErrorEnum.DB_QUERY_ERROR);
  }

}
