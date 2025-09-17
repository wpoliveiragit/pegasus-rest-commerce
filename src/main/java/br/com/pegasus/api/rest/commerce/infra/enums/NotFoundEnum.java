package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.consts.ExceptionConsts;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum NotFoundEnum {
  ELEMENT(1, "Element not found"),
  COOPERATOR(1, "Cooperator not found");

  private final CodeMessageVO codeMsg;

  NotFoundEnum(int code, String msg) {
    this.codeMsg = CodeMessageVO.builder()
        .code(ExceptionConsts.NOT_FOUND_INIT + code)
        .msg(msg)
        .build();
  }
}
