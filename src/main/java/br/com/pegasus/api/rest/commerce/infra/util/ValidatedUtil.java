package br.com.pegasus.api.rest.commerce.infra.util;

import br.com.pegasus.api.rest.commerce.infra.enums.AppEnumException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ValidatedUtil {

  /** Representa o @NotNull: valor não pode ser null. */
  public static void notNull(Object value, AppEnumException appEnumException) {
    if (Objects.isNull(value)) throw appEnumException.getAppException();
  }

  /** Representa o @NotBlank do valid → string não pode ser null, vazia ("") ou só espaços. */
  public static void notBlank(String obj, AppEnumException appEnumException) {
    if (obj == null) throw appEnumException.getAppException();
    if (obj.isBlank()) throw appEnumException.getAppException();
  }

  /** Representa o @PositiveOrZero: número ≤ 0. */
  public static void positiveOrZero(Number obj, AppEnumException appEnumException) {
    if (obj == null) throw appEnumException.getAppException();
    if (obj.doubleValue() <= 0) throw appEnumException.getAppException();
  }

  /** Representa o @Range(min, max): número entre [min, max] */
  public static void range(Number obj, double min, double max, AppEnumException appEnumException) {
    if (obj == null) throw appEnumException.getAppException();
    double val = obj.doubleValue();
    if (val < min || val > max) throw appEnumException.getAppException();
  }

  /** Representa o @Size(min, max): tamanho de string/coleção/array */
  public static void size(Object obj, int min, int max, AppEnumException appEnumException) {
    if (obj == null) throw appEnumException.getAppException();
    int length = (obj instanceof CharSequence cs) ? cs.length() //
        : (obj instanceof Collection<?> c) ? c.size() //
        : (obj instanceof Map<?, ?> m) ? m.size() //
        : (obj instanceof Object[] arr) ? arr.length //
        : -1;
    range(length, min, max, appEnumException);
  }

  /** Representa o @Positive: número < 0. */
  public static void positive(Number obj, AppEnumException appEnumException) {
    if (obj == null) throw appEnumException.getAppException();
    if (obj.doubleValue() < 0) throw appEnumException.getAppException();
  }

}