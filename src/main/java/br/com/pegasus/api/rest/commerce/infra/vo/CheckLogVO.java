package br.com.pegasus.api.rest.commerce.infra.vo;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public final class CheckLogVO {

  private Logger log;
  private String message;

  public CheckLogVO addMessage(String message) {
    this.message += " ⇉ " + message;
    return this;
  }

  public void sendInfo() {
    log.info(message);
  }

  public void sendWarn() {
    log.warn(message);
  }

  public void sendError() {
    log.error(message);
  }
}
