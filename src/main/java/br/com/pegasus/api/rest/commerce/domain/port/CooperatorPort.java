package br.com.pegasus.api.rest.commerce.domain.port;

import br.com.pegasus.api.rest.commerce.domain.model.CooperatorModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageModel;
import br.com.pegasus.api.rest.commerce.domain.model.PageableModel;
import org.springframework.stereotype.Service;

@Service
public interface CooperatorPort {
//TODO: criar o java doc informando todas as necessidade antes de passar.

  /**
   * Retorna uma paginação de elementos.
   *
   * <ul style="list-style-type: disc;">
   *   <li>Size: Não pode ser nulo, não pode ser negativo</li>
   *   <li>Number: Não pode ser nulo, deve ser maior que 0 (zero)</li>
   * </ul>
   *
   * @param inModel A paginação solicitada
   * @return Uma paginação de elementos.
   */
  PageableModel<CooperatorModel> findPage(PageModel inModel);

  CooperatorModel findById(CooperatorModel inModel);

  CooperatorModel create(CooperatorModel inModel);

  void update(CooperatorModel inModel);

  void delete(CooperatorModel inModel);

}
