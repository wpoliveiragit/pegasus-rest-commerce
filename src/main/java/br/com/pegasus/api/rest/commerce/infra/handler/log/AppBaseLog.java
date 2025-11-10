package br.com.pegasus.api.rest.commerce.infra.handler.log;

public interface AppBaseLog {

  void info(String message);

  void info(String message, Object... objs);

}
