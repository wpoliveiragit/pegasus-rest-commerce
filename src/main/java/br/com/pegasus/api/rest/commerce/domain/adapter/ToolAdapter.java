package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductAdaterJPA;

public interface ToolAdapter {

  ProductAdaterJPA getProductRepository();

  LogAdapter getLog(Class<?> c);

  ExceptionMethodAdapter getMethod();

}