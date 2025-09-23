package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum InternalServerErrorEnum {
  DB_QUERY_ERROR(1, "PROBLEMAS NA CONSULTA DO BANCO DE DADOS");

  private final CodeMessageVO codeMsg;

  InternalServerErrorEnum(int code, String msg) {
    this.codeMsg = new CodeMessageVO();
    codeMsg.setCode(ConstUtil.INTERNAL_SERVER_ERROR + code);
    codeMsg.setMsg(msg);
  }
}
