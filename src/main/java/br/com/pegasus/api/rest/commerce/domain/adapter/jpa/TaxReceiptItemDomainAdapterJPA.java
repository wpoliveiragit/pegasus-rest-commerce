package br.com.pegasus.api.rest.commerce.domain.adapter.jpa;

import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import br.com.pegasus.api.rest.commerce.domain.model.TaxReceiptItemModel;

import java.util.Optional;

public interface TaxReceiptItemDomainAdapterJPA {

  PageableModel<TaxReceiptItemModel> findPage(PageModel inModel);

  PageableModel<TaxReceiptItemModel> findPageByTaxReceiptId(PageModel inPageModel, TaxReceiptItemModel inModel);

  Optional<TaxReceiptItemModel> findById(TaxReceiptItemModel inModel);

  TaxReceiptItemModel create(TaxReceiptItemModel inModel);

}
