package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;

public interface ToolKitAdapter {

  // Repository
  ProductDomainAdaterJPA getProductRepository();

  // TOOL
  LogDomainAdapter getLog(Class<?> c);

  MethodDomainAdapter getMethod();

}