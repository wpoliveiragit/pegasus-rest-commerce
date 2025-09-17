package br.com.pegasus.api.rest.commerce.infra.exception;

import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public class CoreRuntimeException extends RuntimeException {

  private final int code;
  private final String message;

  public CoreRuntimeException(CodeMessageVO type) {
    this.code = type.getCode();
    this.message = type.getMsg();
  }

  public CoreRuntimeException(Throwable cause, CodeMessageVO type) {
    super(cause);
    this.code = type.getCode();
    this.message = type.getMsg();
  }

}
