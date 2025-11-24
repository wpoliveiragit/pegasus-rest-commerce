package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;

public interface ExceptionMethodAdapter {
  AppException newNotFound();
  void throwConflictName();
}