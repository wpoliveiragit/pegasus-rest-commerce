package br.com.pegasus.api.rest.commerce.infra.util;

public final class MethodUtil {

  public static boolean isBlank(String value) {
    return value == null || value.isBlank();
  }

  public static boolean isNegative(Number value) {
    return value == null || value.doubleValue() < ConstUtil.N_0;
  }

}