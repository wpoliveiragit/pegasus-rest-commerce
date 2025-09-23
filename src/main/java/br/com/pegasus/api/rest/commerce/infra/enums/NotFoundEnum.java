package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.util.ConstUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum NotFoundEnum {
  ELEMENT(1, "Element not found"),//
  COOPERATOR(2, "Cooperator not found"),//
  PRODUCT(3, "Product not found"),//
  TAX_RECEIPT(4, "Tax Receipt not found");

  private final CodeMessageVO codeMsg;

  NotFoundEnum(int code, String msg) {
    this.codeMsg = new CodeMessageVO();
    codeMsg.setCode(ConstUtil.NOT_FOUND_INIT + code);
    codeMsg.setMsg(msg);
  }
}
