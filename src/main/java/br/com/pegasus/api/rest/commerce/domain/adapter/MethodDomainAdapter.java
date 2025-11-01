package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.AppException;

public interface MethodDomainAdapter {

  AppException newNotFound();

  void throwConflictName();

  String validNameUpdate(String value);

  Float validPriceUpdate(Float value);

  Integer validQualityUpdate(Integer value);

}