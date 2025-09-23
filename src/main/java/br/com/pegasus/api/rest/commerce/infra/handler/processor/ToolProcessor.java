package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.log.CorelLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.HandlerLog;
import lombok.Builder;

import java.lang.reflect.Method;

@Builder(toBuilder = true)
public class ToolProcessor {

  private final HandlerLog log;

  public CorelLog getLog(Method method) {
    return log.getLog(method);
  }
}
