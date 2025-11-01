package br.com.pegasus.api.rest.commerce.infra.log;

public interface AppBaseLog {

  void info(String message);

  void warn(String message);

  void error(String message);

  void info(String message, Object... objs);

  void warn(String message, Object... objs);

  void error(String message, Object... objs);
}
