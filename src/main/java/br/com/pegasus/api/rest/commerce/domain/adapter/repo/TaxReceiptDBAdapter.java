package br.com.pegasus.api.rest.commerce.domain.adapter.repo;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptModel;

import java.util.Optional;

public interface TaxReceiptDBAdapter {

  PageableModel<TaxReceiptModel> findPage(PageModel inModel);

  Optional<TaxReceiptModel> findById(TaxReceiptModel inModel);

  TaxReceiptModel create(TaxReceiptModel inModel);

}