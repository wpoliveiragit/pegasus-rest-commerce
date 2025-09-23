package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.NotFoundCoreException;

public interface ExceptionMethodAdapter {

  NotFoundCoreException newNotFound();

  NotFoundCoreException newCooperatorNotFound();

  NotFoundCoreException newTaxReceiptNotFound();

  NotFoundCoreException newProductNotFound();

  void throwConflictDocumentNumber();

  void throwConflictName();

}