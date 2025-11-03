package br.com.pegasus.api.rest.commerce.infra.config.domain;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogDomainAdapter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class LogDomain implements LogDomainAdapter {

  private final Logger log;

  @Override
  public void info(String msg) {
    log.info(msg);
  }

  @Override
  public void info(String msg, Object... values) {
    log.info(msg, values);
  }

  @Override
  public void info(String msg, Object values) {
    log.info(msg, values);
  }

}