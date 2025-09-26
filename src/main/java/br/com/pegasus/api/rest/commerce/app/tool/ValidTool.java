package br.com.pegasus.api.rest.commerce.app.tool;

import br.com.pegasus.api.rest.commerce.infra.enums.BadRequestEnum;
import br.com.pegasus.api.rest.commerce.infra.util.ValidatedUtil;
import br.com.pegasus.api.rest.commerce.infra.vo.CodeMessageVO;
import br.com.pegasus.gen.openapi.type.CooperatorCreateBodyType;
import br.com.pegasus.gen.openapi.type.CooperatorUpdateBodyType;
import br.com.pegasus.gen.openapi.type.ProductCreateBodyType;
import br.com.pegasus.gen.openapi.type.ProductUpdateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptCreateBodyType;
import br.com.pegasus.gen.openapi.type.TaxReceiptItemCreateBodyType;

public final class ValidTool {

  public static void page(Integer page, Integer size) {
    ValidatedUtil.positive(page, BadRequestEnum.PAGE.getCodeMsg());
    ValidatedUtil.positiveOrZero(size, BadRequestEnum.SIZE.getCodeMsg());
  }

  public static void commonId(Integer id) {
    validId(id, BadRequestEnum.ID.getCodeMsg());
  }

  public static void productId(Integer id) {
    validId(id, BadRequestEnum.PRODUCT_ID.getCodeMsg());
  }

  public static void taxReceiptId(Integer id) {
    validId(id, BadRequestEnum.TAX_RECEIPT_ID.getCodeMsg());
  }

  public static void createBody(CooperatorCreateBodyType body) {
    notNullBody(body);
    validName(body.getName());
    validDocumentNumber(body.getDocumentNumber());
  }

  public static void createBody(TaxReceiptItemCreateBodyType body) {
    notNullBody(body);
    taxReceiptId(body.getTaxReceiptId());
    productId(body.getProductId());
    validQuantity(body.getQuantity());
  }

  public static void createBody(ProductCreateBodyType body) {
    notNullBody(body);
    validName(body.getName());
    validPrice(body.getPrice());
    validQuantity(body.getQuantity());
  }

  public static void createBody(TaxReceiptCreateBodyType body) {
    notNullBody(body);
    commonId(body.getCooperatorId());
  }

  public static void updateBody(Integer id, CooperatorUpdateBodyType body) {
    validId(id, BadRequestEnum.COOPERATOR_ID.getCodeMsg());
    notNullBody(body);
  }

  public static void updateBody(Integer id, ProductUpdateBodyType body) {
    commonId(id);
    notNullBody(body);
  }

  private static void validName(String value) {
    ValidatedUtil.notBlank(value, BadRequestEnum.NAME.getCodeMsg());
  }

  private static void validDocumentNumber(String value) {
    ValidatedUtil.notBlank(value, BadRequestEnum.DOCUMENT_NUMBER.getCodeMsg());
  }

  private static void validPrice(Number value) {
    ValidatedUtil.positiveOrZero(value, BadRequestEnum.PRICE.getCodeMsg());
  }

  private static void validQuantity(Number value) {
    ValidatedUtil.positiveOrZero(value, BadRequestEnum.QUANTITY.getCodeMsg());
  }

  public static void validId(Integer id, CodeMessageVO codeMsg) {
    ValidatedUtil.positiveOrZero(id, codeMsg);
  }

  public static void notNullBody(Object body) {
    ValidatedUtil.notNull(body, BadRequestEnum.BODY.getCodeMsg());
  }

}
