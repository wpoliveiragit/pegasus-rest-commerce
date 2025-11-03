package br.com.pegasus.api.rest.commerce.infra.config.domain;

import br.com.pegasus.api.rest.commerce.domain.adapter.ExceptionMethodDomainAdapter;
import br.com.pegasus.api.rest.commerce.infra.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMethodDomain implements ExceptionMethodDomainAdapter {

  @Override
  public void throwConflictName() {
    throw new AppException(HttpStatus.CONFLICT, "Name already registered");
  }

  @Override
  public AppException newNotFound() {
    return new AppException(HttpStatus.NOT_FOUND, "Element not found");
  }

}