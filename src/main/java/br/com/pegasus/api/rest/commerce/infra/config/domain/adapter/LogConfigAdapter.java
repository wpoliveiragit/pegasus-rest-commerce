package br.com.pegasus.api.rest.commerce.infra.config.domain.adapter;

import br.com.pegasus.api.rest.commerce.domain.adapter.LogAdapter;
import br.com.pegasus.api.rest.commerce.infra.telemetry.logger.TrackLogger;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class LogConfigAdapter implements LogAdapter {

  private final TrackLogger trackLog;
  private final Logger log;

  @Override
  public void track(String msg) {
    trackLog.append(msg);
  }

  @Override
  public void info(String msg) {
    log.info(msg);
  }

}