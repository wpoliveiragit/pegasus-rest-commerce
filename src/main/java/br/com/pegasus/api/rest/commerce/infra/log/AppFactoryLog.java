package br.com.pegasus.api.rest.commerce.infra.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppFactoryLog {

  public static AppBaseLog getCommonLog(Class<?> clazz) {
    return createCommonLog(clazz.getSimpleName());
  }

  private static AppBaseLog createCommonLog(String name) {
    final Logger log = LogManager.getLogger(name);

    return new AppBaseLog() {
      @Override
      public void info(String message) {
        log.info(message);
      }

      @Override
      public void info(String message, Object... objs) {
        log.info(message, objs);
      }

      @Override
      public void warn(String message, Object... objs) {
        log.warn(message, objs);
      }

    };
  }

}
