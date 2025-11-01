package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;

public interface MethodDomainAdapter {

  AppException newNotFound();

  AppException newCooperatorNotFound();

  AppException newTaxReceiptNotFound();

  AppException newProductNotFound();

  void throwConflictDocumentNumber();

  void throwConflictName();

  boolean isNotBlank(String value);

  void validDocumentNumber(String documentNumber);

  void validPrice(Number value);

  void validQuantity(Number value);

}