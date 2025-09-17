package br.com.pegasus.api.rest.commerce.infra.enums;

import br.com.pegasus.api.rest.commerce.infra.consts.ExceptionConsts;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import lombok.Getter;

@Getter
public enum BadRequestEnum {
  PAGE(1, "Page"),//
  SIZE(2, "Size"),//
  BODY(3, "Body"),//
  ID(4, "Id"),//
  COOPERATOR_ID(5, "Cooperator Id"),//
  PRODUCT_ID(6, "Product Id"),//
  TAX_RECEIPT_ID(7, "Tax Receipt Id"),//
  NAME(8, "Name"),//
  DOCUMENT_NUMBER(9, "Document Number"),//
  PRICE(10, "Price"),//
  QUANTITY(11, "Quantity");

  private final CodeMessageVO codeMsg;

  BadRequestEnum(int code, String msg) {
    this.codeMsg = CodeMessageVO.builder()
        .code(ExceptionConsts.BAD_REQUEST_INIT + code)
        .msg(msg)
        .build();
  }
}
