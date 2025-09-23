package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum ConflictEnum {
  EXISTING_ELEMENT(1, "Elemento já Cadastrado"),//
  EXISTING_NAME(1, "Nome já cadastrado"),//
  EXISTING_DOCUMENT_NUMBER(1, "CPF já cadastrado");

  private final CodeMessageVO codeMsg;

  ConflictEnum(int code, String msg) {
    this.codeMsg = new CodeMessageVO();
    codeMsg.setCode(ConstUtil.CONFLICT_INIT + code);
    codeMsg.setMsg(msg);
  }
}
