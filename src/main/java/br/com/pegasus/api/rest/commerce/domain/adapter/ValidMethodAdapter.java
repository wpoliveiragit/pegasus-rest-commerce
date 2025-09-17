package br.com.pegasus.api.rest.commerce.domain.adapter;

import br.com.pegasus.api.rest.commerce.infra.exception.BadRequestCoreException;

public interface ValidMethodAdapter {

  boolean isNotBlank(String value);

  /**
   * Retorna o parâmetro value se ele não for nulo nem branco.
   * @param value valor que deseja obter em caso de sucesso.
   * @param defaultValue valor que obtera em caso de fracasso
   * @return O parâmetro value ou defaultValue.
   */
  String getValueNotBlank(String value, String defaultValue);

  /**
   * Retorna o parâmetro value se ele não for nulo nem valor negativo.
   * @param value valor que deseja obter em caso de sucesso.
   * @param defaultValue valor q receberá se  for nulo ou negativo.
   * @return O parâmetro value ou defaultValue.
   * @param <T> O tipo do retorno que deve extender de um Number.
   */
  <T extends Number> T getValueNotNegative(T value, T defaultValue);

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
