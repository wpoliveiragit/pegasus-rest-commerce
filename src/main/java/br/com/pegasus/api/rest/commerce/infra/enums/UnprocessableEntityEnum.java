package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum UnprocessableEntityEnum {
  INVALID_DOC_NUM(1, "Document Number: Invalid");

  private final CodeMessageVO codeMsg;

  UnprocessableEntityEnum(int code, String msg) {
    this.codeMsg = new CodeMessageVO();
    codeMsg.setCode(ConstUtil.UNPROCESSABLE_INIT + code);
    codeMsg.setMsg(msg);
  }

}

