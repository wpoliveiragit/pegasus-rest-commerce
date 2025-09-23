package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.repo.CooperatorDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.ProductDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptDBAdapter;
import br.com.pegasus.api.rest.commerce.domain.adapter.repo.TaxReceiptItemDBAdapter;

public interface ToolKitAdapter {

  // Repository
  CooperatorDBAdapter getCooperatorRepository();

  ProductDBAdapter getProductRepository();

  TaxReceiptItemDBAdapter getTaxReceiptItemRepository();

  TaxReceiptDBAdapter getTaxReceiptRepository();

  // TOOL
  LogAdapter getLog(Class<?> c);

  ValidMethodAdapter getValidMethod();

  ExceptionMethodAdapter getExceptionMethod();

}