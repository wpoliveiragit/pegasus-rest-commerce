package br.com.pegasus.api.rest.commerce.infra.handler.processor;

import br.com.pegasus.api.rest.commerce.infra.handler.log.CorelLog;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class InvocationHandlerProcessor implements InvocationHandler {

  private final Object bean;
  private final ToolProcessor tool;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    CorelLog log = tool.getLog(method);
    try {
      log.startt();
      Object result = method.invoke(bean, args);
      log.endd();
      return result;
    } catch (Exception ex) {
      log.exceptionn();
      throw ex;
    } finally {
      log.finallyy();
    }
  }

}
