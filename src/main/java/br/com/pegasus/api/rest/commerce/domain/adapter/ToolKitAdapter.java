package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;

public interface ToolKitAdapter {

  ProductDomainAdaterJPA getProductRepository();

  LogDomainAdapter getLog(Class<?> c);

  ExceptionMethodDomainAdapter getMethod();

}