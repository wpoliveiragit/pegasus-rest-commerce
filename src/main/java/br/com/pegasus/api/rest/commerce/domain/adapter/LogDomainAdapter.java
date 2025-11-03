package br.com.pegasus.api.rest.commerce.domain.adapter;

public interface LogDomainAdapter {

  void info(String msg);

  void info(String msg, Object[] obj);

  void info(String msg, Object obj);

}
