package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;

public interface ValidMethodAdapter {

  boolean isNotBlank(String value);

  /**
   * Verifica se o cpf é valido:
   *
   * <ul style="list-style-type: disc;">
   *   <li>Se o parametro tem tamanho 11</li>
   *   <li>Se os 11 dígitos não são iguais (ex: 00000000000)</li>
   *   <li>Se o cpf é logicamente válido</li>
   *   <li>Não verifica parametro nulo</li>
   *   <li><span style="color:#808080">Não verifica parametro nulo nem branco</span></b></li>
   *   <li><span style="color:#808080">Se alguma verificação falhar será disparado um </span>{@link BadRequestCoreException}</b></li>
   * </ul>
   *
   * @param documentNumber O CPF a ser verificado.
   */
  void validDocumentNumber(String documentNumber);

  /**
   * Verifica se o preço é valido
   *
   * <ul style="list-style-type: disc;">
   *   <li>Não pode ser nulo</li>
   *   <li>Não pode ser menor que 0</li>
   *   <li><span style="color:#808080">Se alguma verificação falhar será disparado um </span>{@link BadRequestCoreException}</b></li>
   * </ul>
   *
   * @param value valor que deseja obter em caso de sucesso.
   */
  void validPrice(Number value);

  void validQuantity(Number value);

}