package br.com.pegasus.api.rest.commerce.infra.util;

import org.apache.logging.log4j.message.ParameterizedMessage;

public final class TextFormatUtil {

  public static String format(String message, Object... objs) {
    return ParameterizedMessage.format(message, objs);
  }

  public static String addColorGreen(String text) {
    return ConstUtil.COLOR_GREEN + text + ConstUtil.COLOR_RESET;
  }

  public static String addColorBlue(String text) {
    return ConstUtil.COLOR_BLUE + text + ConstUtil.COLOR_RESET;
  }

  public static String addColorCyan(String text) {
    return ConstUtil.COLOR_CYAN + text + ConstUtil.COLOR_RESET;
  }

}
