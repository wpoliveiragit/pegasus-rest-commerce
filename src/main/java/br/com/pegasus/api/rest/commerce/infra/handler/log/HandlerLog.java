package br.com.pegasus.api.rest.commerce.infra.handler.log;

import lombok.Builder;

import java.lang.reflect.Method;
import java.util.Map;

@Builder
public class HandlerLog {

  private Class<?> clazz;
  private Map<Method, CorelLog> logMap;
  private final CorelLog defaultLog = new CorelLog();

  public CorelLog getLog(Method method) {
    try {
      return logMap.getOrDefault(clazz.getMethod(method.getName(), method.getParameterTypes()), defaultLog);
    } catch (Exception ex) {
      return defaultLog;
    }
  }

}
