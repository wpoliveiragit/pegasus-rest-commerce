package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.CooperatorDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.ProductDomainAdaterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptDomainAdapterJPA;
import br.com.pegasus.api.rest.commerce.domain.adapter.jpa.TaxReceiptItemDomainAdapterJPA;

public interface ToolKitAdapter {

  // Repository
  CooperatorDomainAdapterJPA getCooperatorRepository();

  ProductDomainAdaterJPA getProductRepository();

  TaxReceiptItemDomainAdapterJPA getTaxReceiptItemRepository();

  TaxReceiptDomainAdapterJPA getTaxReceiptRepository();

  // TOOL
  LogDomainAdapter getLog(Class<?> c);

  MethodDomainAdapter getMethod();

}