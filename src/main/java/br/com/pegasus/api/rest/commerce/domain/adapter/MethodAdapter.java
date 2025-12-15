package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;

import java.time.OffsetDateTime;

public interface MethodAdapter {
  AppException newNotFoundException();
  void throwConflictNameException();
  OffsetDateTime getOffsetDateTimeNow();
}