package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.consts.ExceptionConsts;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum InternalServerErrorEnum {
  DB_QUERY_ERROR(1, "PROBLEMAS NA CONSULTA DO BANCO DE DADOS");

  private final CodeMessageVO codeMsg;

  InternalServerErrorEnum(int code, String msg) {
    this.codeMsg = CodeMessageVO.builder()
        .code(ExceptionConsts.INTERNAL_SERVER_ERROR + code)
        .msg(msg)
        .build();
  }
}
