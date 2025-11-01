package br.com.pegasus.api.rest.commerce.infra.handler.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Map;

@Getter
@AllArgsConstructor
public class HandlerLog {

  private Class<?> clazz;
  private Map<Method, CorelLog> logMap;
  private final CorelLog defaultLog = new CorelLog();

  public CorelLog getLog(Method method){
    return logMap.get(method);
  }

}
