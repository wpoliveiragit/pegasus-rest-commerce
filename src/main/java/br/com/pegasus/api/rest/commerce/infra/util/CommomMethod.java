package br.com.pegasus.api.rest.commerce.infra.util;

import br.com.pegasus.api.rest.commerce.infra.enums.InternalServerErrorEnum;
import br.com.pegasus.api.rest.commerce.infra.exception.InternalServerErrorCoreException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.function.Supplier;

public final class CommomMethod {

  public static boolean isBlank(String value) {
    return value == null || value.isBlank();
  }

  public static boolean isNegative(Number value) {
    return value == null || value.doubleValue() < ConstUtil.N_0;
  }

  public static String getValueNotBlank(String value, String defaultValue){
    return isBlank(value)? defaultValue : value;
  }

  public static <T extends Number> T getValueNotNegative(T value, T defaultValue) {
    return isNegative(value) ? defaultValue : value;
  }

  public static OffsetDateTime toOffsetDateTime(LocalDateTime date) {
    return date.atZone(ZoneId.systemDefault()).toOffsetDateTime();
  }

  /**
   * Executa qualquer função ou lambda que não receba parametro e tenha um retorno.
   *
   * @param task a função ou lambda
   * @return o Retorno da função ou lambda
   * @param <T> O tipo do retorno
   */
  public static <T> T funcionalExecute(Supplier<T> task) {
    try {
      return task.get();
    } catch (Exception ex) {
      throw InternalServerErrorCoreException.bdQueryError(ex);
    }
  }

  /**
   * Executa qualquer função ou lambda que não receba parametro e nem tenha um retorno.
   * @param task a função ou lambda
   */
  public static void executeVoid(Runnable task) {
    try {
      task.run();
    } catch (Exception ex) {
      throw new InternalServerErrorCoreException(ex, InternalServerErrorEnum.DB_QUERY_ERROR.getCodeMsg());
    }
  }


}
