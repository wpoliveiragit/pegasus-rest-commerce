package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.log.CorelLog;
import br.com.pegasus.api.rest.commerce.infra.handler.log.HandlerLog;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class ToolProcessor {

  private  HandlerLog log;

  public CorelLog getLog(Method method) {
    return log.getLog(method);
  }
}
