package br.com.pegasus.api.rest.commerce.infra.handler.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppFactoryLog {

  public static AppBaseLog getCommonLog(Class<?> clazz) {
    return createCommonLog(clazz.getSimpleName());
  }

  private static AppBaseLog createCommonLog(String name) {
    final Logger log = LogManager.getLogger(name);

    return new AppBaseLog() {
      public @Override void info(String message) {
        log.info(message);
      }

      public @Override void info(String message, Object... objs) {
        log.info(message, objs);
      }
    };
  }

}
