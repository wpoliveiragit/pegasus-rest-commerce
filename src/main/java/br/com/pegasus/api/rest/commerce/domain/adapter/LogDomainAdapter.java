package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface LogDomainAdapter {

  void info(String msg);

  void erro(String msg);

  void warn(String msg);

  void info(String msg, Object[] obj);

  void warn(String msg, Object[] obj);

  void erro(String msg, Object[] obj);
}
