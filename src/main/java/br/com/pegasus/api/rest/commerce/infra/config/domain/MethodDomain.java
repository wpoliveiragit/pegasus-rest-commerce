package br.com.pegasus.api.rest.commerce.infra.config.domain;

import br.com.pegasus.api.rest.commerce.domain.adapter.MethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.infra.enums.AppEnumException;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import br.com.pegasus.api.rest.commerce.infra.util.CpfUtil;
import br.com.pegasus.api.rest.commerce.infra.util.MethodUtil;
import org.springframework.stereotype.Component;

@Component
public class MethodDomain implements MethodDomainAdapter {

  @Override
  public void throwConflictDocumentNumber() {
    throw AppEnumException.CONFLICT_DOCUMENT_NUMBER.getAppException();
  }

  @Override
  public void throwConflictName() {
    throw AppEnumException.CONFLICT_NAME.getAppException();
  }

  @Override
  public AppException newNotFound() {
    return AppEnumException.NOT_FOUND_ELEMENT.getAppException();
  }

  @Override
  public AppException newCooperatorNotFound() {
    return AppEnumException.NOT_FOUND_COOPERATOR.getAppException();
  }

  @Override
  public AppException newTaxReceiptNotFound() {
    return AppEnumException.NOT_FOUND_TAX_RECEIPT.getAppException();
  }

  @Override
  public AppException newProductNotFound() {
    return AppEnumException.NOT_FOUND_PRODUCT.getAppException();
  }

  @Override
  public boolean isNotBlank(String value) {
    return !MethodUtil.isBlank(value);
  }

  @Override
  public void validDocumentNumber(String documentNumber) {
    if (!CpfUtil.valid(documentNumber)) throw AppEnumException.BAD_REQUEST_DOCUMENT_NUMBER.getAppException();
  }

  @Override
  public void validPrice(Number value) {
    if (MethodUtil.isNegative(value)) throw AppEnumException.BAD_REQUEST_PRICE.getAppException();
  }

  @Override
  public void validQuantity(Number value) {
    if (MethodUtil.isNegative(value)) throw AppEnumException.BAD_REQUEST_QUANTITY.getAppException();
  }

}