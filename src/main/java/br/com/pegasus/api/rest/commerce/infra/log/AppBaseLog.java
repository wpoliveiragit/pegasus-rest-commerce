package br.com.pegasus.api.rest.commerce.infra.log;

public interface AppBaseLog {

  void info(String message);

  void info(String message, Object... objs);

  void warn(String message, Object... objs);

}
